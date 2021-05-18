package util;

public class SimpleErrorHandler implements ErrorHandler {

	@Override
	public void handle(Exception e, int code) {
		System.err.println("Error code: " + code + (code == -1 ? " (which means unspecified)" : ""));
		e.printStackTrace();
	}

}
