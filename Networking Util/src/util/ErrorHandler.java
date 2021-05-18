package util;

@FunctionalInterface
public interface ErrorHandler {

	//-1 means no error code specified
	public abstract void handle(Exception e, int code);
		
}
