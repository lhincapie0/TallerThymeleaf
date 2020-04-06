package co.edu.icesi.fi.tics.tssc.exceptions;

public class NotExistingTopic extends Exception{

	public NotExistingTopic() {
		super("The topic does not exist");
	}
}
