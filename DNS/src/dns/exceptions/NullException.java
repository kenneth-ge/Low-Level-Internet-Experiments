package dns.exceptions;

public class NullException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NullException() {
		super("null! oh no! print out the stack trace to figure out why");
	}
	
}
