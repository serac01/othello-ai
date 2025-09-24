/*
	Our own exception class for the time control
 */
public class TimeUpException extends Exception {

	public TimeUpException() { super("Time limit reached"); }
}