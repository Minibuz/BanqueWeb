package javaBeans;

import gestionErreurs.TraitementException;

public class TestConsultation {
	
	public static void main(String[] args) throws TraitementException {
		BOperations test = new BOperations();
		test.ouvrirConnexion();
		System.out.println("Connecté");
		test.setNoDeCompte("0001");
		test.consulter();
		System.out.println("N° de compte: " + test.getNoDeCompte());
		System.out.println("Nom: " + test.getNom());
		System.out.println("Prénom: " + test.getPrenom());
		System.out.println("Solde: " + test.getSolde());
		test.fermerConnexion();
	}
}
