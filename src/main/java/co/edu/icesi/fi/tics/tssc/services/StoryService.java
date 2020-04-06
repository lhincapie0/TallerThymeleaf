package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface StoryService {

	public TsscStory saveStory(TsscStory story, TsscGame game) throws NullStoryException, BusinessValueException, InitialSprintException, PriorityException, NullGameException, NotExistingGameException;
	public TsscStory editStory(TsscStory story) throws NullStoryException, NotExistingStory, PriorityException, BusinessValueException, InitialSprintException, NotExistingGameException;

}
