package dns.exceptions;

public class RegisterFailed extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegisterFailed(String s) {
		super(s);
	}
	
}
