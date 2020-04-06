package co.edu.icesi.fi.tics.tssc.exceptions;

public class NotExistingStory extends Exception{

	public NotExistingStory() {
		super("The topic does not exist");
	}
}
