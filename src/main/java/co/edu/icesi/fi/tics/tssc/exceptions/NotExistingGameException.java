package co.edu.icesi.fi.tics.tssc.exceptions;

public class NotExistingGameException extends Exception{

	public NotExistingGameException() {
		super("The game does not exist");
	}
}
