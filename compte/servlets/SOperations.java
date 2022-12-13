package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaBeans.BOperations;

import java.io.IOException;
import java.math.BigDecimal;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import gestionErreurs.TraitementException;

/**
 * Servlet implementation class SOperations
 */
public class SOperations extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DataSource dataSource = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SOperations() {
        super();
    }

    /**
     * Initialise the datasource
     */
    public void init() {
    	try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/Banque");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Get the request and the response given and process the event given according to datas inside
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String where = request.getParameter("button");
		if(where == null) {
			where = "";
		}
		
		System.out.println(where);
		
		switch (where) {
		case "": {
			this.getServletContext().getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
			return;
		}
		case "consulter": {
			String noCompte = request.getParameter("noDeCompte");
			try {
				verifNoDeCompte(noCompte);
				
				BOperations operation = new BOperations();
				operation.ouvrirConnexion(dataSource);
				operation.setNoDeCompte(noCompte);
				operation.consulter();
				
				request.getSession().setAttribute("BOperation", operation);
				
				request.getSession().setAttribute("errorConsulter", null);
				request.getSession().setAttribute("noCompte", null);
				
				this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
				operation.fermerConnexion();
				return;
			} catch (TraitementException e) {
				request.getSession().setAttribute("errorConsulter", e.getMessage());
				request.getSession().setAttribute("noCompte", noCompte);
				this.getServletContext().getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
				return;
			}
		}
		case "traiter": {
			String op = request.getParameter("op");
			String valEnt = request.getParameter("valeurEntiere");
			String valDec = request.getParameter("valeurDecimale");
			
			try {
				verifValeur(valEnt + "." + valDec);
				
				BOperations operation = (BOperations) request.getSession().getAttribute("BOperation");
				operation.ouvrirConnexion(dataSource);
				
				operation.setOp(op);
				operation.setValeurEntiere(valEnt);
				operation.setValeurDecimale(valDec);
				operation.setValeur(valEnt + "." + valDec);
				
				operation.traiter();
				
				request.getSession().setAttribute("BOperation", operation);
				request.getSession().setAttribute("errorTraiter", null);
				
				this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
				operation.fermerConnexion();
				return;
			} catch (TraitementException e) {
				request.getSession().setAttribute("errorTraiter", e.getMessage());
				
				this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
				return;
			}
		}
		case "demande": {
			String jInit = request.getParameter("jInit");
			if(jInit.startsWith(" 0 + ")) {
				jInit = jInit.substring(5);
			}
			String mInit = request.getParameter("mInit");
			if(mInit.startsWith(" 0 + ")) {
				mInit = mInit.substring(5);
			}
			String aInit = request.getParameter("aInit");
			String jFinal = request.getParameter("jFinal");
			if(jFinal.startsWith(" 0 + ")) {
				jFinal = jFinal.substring(5);
			}
			String mFinal = request.getParameter("mFinal");
			if(mFinal.startsWith(" 0 + ")) {
				mFinal = mFinal.substring(5);
			}
			String aFinal = request.getParameter("aFinal");
			
			try {
			
				BOperations operation = (BOperations) request.getSession().getAttribute("BOperation");
				operation.ouvrirConnexion(dataSource);
				
				String dateInit = aInit + "/" + mInit + "/" + jInit;
				String dateFinal = aFinal + "/" + mFinal + "/" + jFinal;
				
				verifDate(aInit, mInit, jInit, aFinal, mFinal, jFinal);
				
				operation.setDateInf(dateInit); // yyyy/MM/dd
				operation.setDateSup(dateFinal); // yyyy/MM/dd
				
				operation.listerParDates();
				
				if(operation.getOperationsParDates().isEmpty()) {
					throw new TraitementException("32");
				}
				
				request.getSession().setAttribute("BOperation", operation);
				request.getSession().setAttribute("errorDate", null);
				
				this.getServletContext().getRequestDispatcher("/JListOperations.jsp").forward(request, response);
				operation.fermerConnexion();
				return;
			} catch (Exception e) {
				request.getSession().setAttribute("errorDate", e.getMessage());
				this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
				return;
			}
		}
		case "return": {
			this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
			
			// TODO : Back work properly
			
			return;
		}
		case "end": {
			request.getSession().removeAttribute("errorDate");
			request.getSession().removeAttribute("errorTraiter");
			request.getSession().removeAttribute("BOperation");
			this.getServletContext().getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + where);
		}
	}

	/**
	 * Check if the account number respects the project standards
	 * @param noCompte
	 * @throws TraitementException
	 */
	private void verifNoDeCompte(String noCompte) throws TraitementException {
		if(!noCompte.matches("[0-9]*")) {
			throw new TraitementException("4");
		}
		if(noCompte.length() != 4) {
			throw new TraitementException("5");
		}
	}
	
	/**
	 * Check if the value given respects the projects standards
	 * @param valeur
	 * @throws TraitementException
	 */
	private void verifValeur(String valeur) throws TraitementException {
		if(".".equals(valeur)) {
			throw new TraitementException("26");
		}
		try {
			new BigDecimal(valeur);
		} catch(NumberFormatException e) {
			throw new TraitementException("25");
		}	
	}
	
	/**
	 * Check if the values given can generate a date respecting the project standards
	 * @param aInit
	 * @param mInit
	 * @param jInit
	 * @param aFinal
	 * @param mFinal
	 * @param jFinal
	 * @throws TraitementException
	 */
	private void verifDate(String aInit, String mInit, String jInit, String aFinal, String mFinal, String jFinal) throws TraitementException {
		if(Integer.parseInt(aFinal) < Integer.parseInt(aInit)) {
			throw new TraitementException("31");
		}
		if(Integer.parseInt(aFinal) > Integer.parseInt(aInit)) {
			return;
		}
		
		if(Integer.parseInt(mFinal) < Integer.parseInt(mInit)) {
			throw new TraitementException("31");
		}
		if(Integer.parseInt(mFinal) > Integer.parseInt(mInit)) {
			return;
		}
		
		if(Integer.parseInt(jFinal) < Integer.parseInt(jInit)) {
			throw new TraitementException("31");
		}
	}
}
