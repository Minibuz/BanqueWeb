package gestionErreurs;

public class TraitementException extends Exception {

	private static final long serialVersionUID = 4049573491969878977L;

	/**
	 * Compute the given text containing a number to get the associated error message
	 * @param str
	 */
	public TraitementException(String str) {
		super(MessagesDErreurs.getMessageDerreur(str));
	}
}
