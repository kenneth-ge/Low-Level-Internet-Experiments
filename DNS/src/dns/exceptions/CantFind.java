package dns.exceptions;

public class CantFind extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CantFind(String s) {
		super(s);
	}
	
}
