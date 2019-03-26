package mz.co.basse.core.exception;

/**
 * Representa uma condicao de Duplicação de Entidades. Usado nos
 * inserts para apresentar mensagem de erro de duplicação.
 * 
 * 
 */
public class DuplicateEntryException extends RuntimeException {

	private static final long serialVersionUID = -1337798242882649722L;

	public DuplicateEntryException(String message) {
		super(message);
	}
}
