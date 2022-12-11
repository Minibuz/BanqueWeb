package gestionErreurs;

import java.util.HashMap;

public class MessagesDErreurs {
	private static HashMap<String, String> message = new HashMap<>(){{
		put("3", "Problème pour accéder à ce compte client, vérifiez qu'il est bien valide");
		put("4", "Le N° de compte doit être numérique");
		put("5", "Le N° du compte doit comporter 4 caractères");
		put("21", "Problème d'accès à la base de données, veuillez le signaler à votre administrateur");
		put("22", "Problème après traitement. Le traitement a été effectué correctement mais il y a eu un problème à signaler à votre administrateur");
		put("24", "Opération refusée, début demandé supérieur au crédit du compte");
		put("25", "La valeur doit être numérique");
		put("26", "Aucune valeur n'a été saisie");
		put("31", "La date initiale doit être inférieur à la date finale");
		put("32", "Il n'y a eu aucune opération effectuée durant cette période");
	}};
	
	public static String getMessageDerreur(String numeroMessage) {
		return message.get(numeroMessage);
	}
}
