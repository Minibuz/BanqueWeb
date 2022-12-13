package javaBeans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javax.sql.DataSource;

import gestionErreurs.TraitementException;

public class BOperations {
	
	/*
	 * 
	 * 			Champs
	 * 
	 */
	private String noCompte;
	
	private String nom;
	private String prenom;
	private java.math.BigDecimal solde;
	
	private java.math.BigDecimal ancienSolde;
	private java.math.BigDecimal nouveauSolde;
	private java.math.BigDecimal valeur;
	private String valeurEntiere;
	private String valeurDecimale;
	private String op;
	
	private ArrayList<String> operationsParDates;
	private String dateInf;
	private String dateSup;
	
	private Connection connection;
	
	
	/**
	 * Get the account number
	 * @return the account number
	 */
	public String getNoDeCompte() {
		return noCompte;
	}
	/**
	 * Set the given value as account number
	 * @param noCompte
	 */
	public void setNoDeCompte(String noCompte) {
		this.noCompte = noCompte;
	}
	/**
	 * Get the account name
	 * @return the account name
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * Get the account first name
	 * @return the account first name
	 */
	public String getPrenom() {
		return prenom;
	}
	/**
	 * Get the account balance
	 * @return the account balance
	 */
	public java.math.BigDecimal getSolde() {
		return solde;
	}
	/**
	 * Get the account old balance
	 * @return the account old balance
	 */
	public java.math.BigDecimal getAncienSolde() {
		return ancienSolde;
	}
	
	/**
	 * Get the account new balance
	 * @return the account new balance
	 */
	public java.math.BigDecimal getNouveauSolde() {
		return nouveauSolde;
	}
	
	/**
	 * Get the registered value
	 * @return the registered value
	 */
	public String getValeur() {
		return valeur.setScale(2, RoundingMode.FLOOR).toString();
	}
	
	/**
	 * Set the given value as the registered value
	 * @param valeur
	 */
	public void setValeur(String valeur) {
		this.valeur = new BigDecimal(valeur);
	}
	
	/**
	 * Get the registered integer value
	 * @return the registered integer value
	 */
	public String getValeurEntiere() {
		return valeurEntiere;
	}
	
	/**
	 * Set the given integer value as the registered integer value
	 * @param valeurEntiere
	 */
	public void setValeurEntiere(String valeurEntiere) {
		if(valeurEntiere == null) {
			this.valeurEntiere = "";
		} else {
			this.valeurEntiere = valeurEntiere;
		}
	}
	
	/**
	 * Get the registered decimal value
	 * @return
	 */
	public String getValeurDecimale() {
		return valeurDecimale;
	}
	
	/**
	 * Set the given decimal value as the registered decimal value
	 * @param valeurDecimale
	 */
	public void setValeurDecimale(String valeurDecimale) {
		if(valeurDecimale == null) {
			this.valeurDecimale = "";
		} else {
			this.valeurDecimale = valeurDecimale;
		}
	}
	
	/**
	 * Get the registered operation
	 * @return the registered operation
	 */
	public String getOp() {
		return op;
	}
	
	/**
	 * Set the given operation as the operation registered
	 * @param op
	 */
	public void setOp(String op) {
		this.op = op;
	}
	/**
	 * Get the minimal date
	 * @return the minimal date
	 */
	public String getDateInf() {
		return dateInf;
	}
	/**
	 * Set the minimal date given as the minimal date registered
	 * @param dateInf
	 */
	public void setDateInf(String dateInf) {
		this.dateInf = dateInf;
	}
	/**
	 * Get the maximal date registered
	 * @return the maximal date
	 */
	public String getDateSup() {
		return dateSup;
	}
	/**
	 * Set the given maximal date as the maximal date registered
	 * @param dateSup
	 */
	public void setDateSup(String dateSup) {
		this.dateSup = dateSup;
	}
	/**
	 * Get the list of operations processed between the minimal and the maximal date registered
	 * @return the list of operations
	 */
	public ArrayList<String> getOperationsParDates() {
		return operationsParDates;
	}
	
	/**
	 * Open the connection with the database
	 * @throws TraitementException
	 */
	public void ouvrirConnexion() throws TraitementException {
		String name = "localhost";
		String bddName = "jee_db";
		String port = "3306";
		
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://"+name+":"+port+"/"+bddName,
					"root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TraitementException("21");
		}
	}
	
	/**
	 * Open the connection with the database according to the given datasource
	 * @throws TraitementException
	 */
	public void ouvrirConnexion(DataSource ds) throws TraitementException {
		if(ds == null) {
			throw new TraitementException("21");
		}
		
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TraitementException("21");
		}
	}
	/**
	 * Close the connection with the database
	 * @throws TraitementException
	 */
	public void fermerConnexion() throws TraitementException {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TraitementException("22");
		}
	}
	
	/**
	 * Consult the account associated to the registered account number
	 * @throws TraitementException
	 */
	public void consulter() throws TraitementException {
		Objects.requireNonNull(connection);
		Objects.requireNonNull(noCompte);
		
		try(Statement stmt = connection.createStatement()) {
			String consult = "SELECT * FROM COMPTE AS C WHERE C.NOCOMPTE='"+noCompte+"'";
			ResultSet rs = stmt.executeQuery(consult);
			while (rs.next()) {
			    nom = rs.getString("nom");
				prenom = rs.getString("prenom");
				solde = rs.getBigDecimal("solde");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TraitementException("3");
		}
	}
	
	/**
	 * Compute the operation registered on the account associated to the registered account number
	 * @throws TraitementException
	 */
	public void traiter() throws TraitementException {
		Objects.requireNonNull(connection);
		Objects.requireNonNull(noCompte);
		Objects.requireNonNull(op);
		Objects.requireNonNull(valeur);
		
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			throw new TraitementException("21");
		}
		
		try(Statement stmt = connection.createStatement()) {
			String consult = "SELECT SOLDE FROM COMPTE AS C WHERE C.NOCOMPTE='"+noCompte+"'";
			ResultSet rs = stmt.executeQuery(consult);
			while (rs.next()) {
			    ancienSolde = rs.getBigDecimal("solde");
			}
			
			if("+".equals(op)) {
				nouveauSolde = ancienSolde.add(valeur);
			} else if("-".equals(op)) {
				nouveauSolde = ancienSolde.subtract(valeur);
			} else {
				throw new IllegalArgumentException(op + " is not recognized.");
			}
			
			if(nouveauSolde.signum() == -1) {
				throw new TraitementException("24");
			}
			
			String updateCompte = "UPDATE COMPTE SET solde=" + nouveauSolde + " WHERE noCompte='" + noCompte + "';";
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			String addOp = "INSERT INTO OPERATIONS(NOCOMPTE, DATE, HEURE, OP, VALEUR) VALUES('" + noCompte + "','" + dateFormat.format(date).toString() + "','" + timeFormat.format(date).toString() + "','" + op + "'," + valeur + ");";
			
			try {
				stmt.executeUpdate(updateCompte);
				stmt.executeUpdate(addOp);
				connection.commit();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				connection.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new TraitementException("21");
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new TraitementException("21");
			}
		}
	}
	
	/**
	 * Fill the operationsParDates list with all the operations made between
	 * the minimal date registered and the maximal date registered
	 * @throws TraitementException
	 */
	public void listerParDates() throws TraitementException {
		Objects.requireNonNull(connection);
		Objects.requireNonNull(noCompte);
		Objects.requireNonNull(dateInf);
		Objects.requireNonNull(dateSup);
		
		operationsParDates = new ArrayList<>();
		
		try(Statement stmt = connection.createStatement()) {
			String consult = "SELECT * "
					+ "FROM OPERATIONS AS O "
					+ "WHERE O.NOCOMPTE='" + noCompte + "' "
					+ "AND O.DATE BETWEEN '" + dateInf + "' AND '" + dateSup + "';";
			
			ResultSet rs = stmt.executeQuery(consult);
			
			while (rs.next()) {
			    operationsParDates.add(rs.getString("DATE") + " " + rs.getString("OP") + " " + rs.getBigDecimal("VALEUR"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("AAAAAAAAAAA");
			throw new TraitementException("21");
		}
	}
}
