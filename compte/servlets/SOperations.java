package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaBeans.BOperations;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

/**
 * Servlet implementation class SOperations
 */
public class SOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SOperations() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
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
				operation.ouvrirConnexion();
				operation.setNoDeCompte(noCompte);
				operation.consulter();
				
				request.getSession().setAttribute("BOperation", operation);
				
				this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
				operation.fermerConnexion();
				return;
			} catch (TraitementException e) {
				request.setAttribute("error", e.getMessage());
				request.setAttribute("noCompte", noCompte);
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
				operation.ouvrirConnexion();
				
				operation.setOp(op);
				operation.setValeurEntiere(valEnt);
				operation.setValeurDecimale(valDec);
				operation.setValeur(valEnt + "." + valDec);
				
				operation.traiter();
				
				request.getSession().setAttribute("BOperation", operation);
				
				this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
				operation.fermerConnexion();
				return;
			} catch (TraitementException e) {
				request.setAttribute("error", e.getMessage());
				
				this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
				return;
			}
		}
		case "demande": {
			String jInit = request.getParameter("jInit");
			String mInit = request.getParameter("mInit");
			String aInit = request.getParameter("aInit");
			String jFinal = request.getParameter("jFinal");
			String mFinal = request.getParameter("mFinal");
			String aFinal = request.getParameter("aFinal");
			
			try {
			
				BOperations operation = (BOperations) request.getSession().getAttribute("BOperation");
				operation.ouvrirConnexion();
				
				operation.setDateInf(aInit + "/" + mInit + "/" + jInit); // yyyy/MM/dd
				operation.setDateSup(aFinal + "/" + mFinal + "/" + jFinal); // yyyy/MM/dd
				
				operation.listerParDates();
				
				request.getSession().setAttribute("BOperation", operation);
				
				this.getServletContext().getRequestDispatcher("/JListOperations.jsp").forward(request, response);
				operation.fermerConnexion();
				return;
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				return;
			}
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + where);
		}
	}

	private void verifNoDeCompte(String noCompte) throws TraitementException {
		if(!noCompte.matches("[0-9]*")) {
			throw new TraitementException("4");
		}
		if(noCompte.length() != 4) {
			throw new TraitementException("5");
		}
	}
	
	private void verifValeur(String valeur) throws TraitementException {
		System.out.println(valeur);
		
		if(".".equals(valeur)) {
			throw new TraitementException("26");
		}
		try {
			new BigDecimal(valeur);
		} catch(NumberFormatException e) {
			throw new TraitementException("25");
		}
		
	}
}
