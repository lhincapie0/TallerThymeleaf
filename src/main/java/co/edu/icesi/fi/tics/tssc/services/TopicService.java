package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface TopicService {
	
	public TsscTopic saveTopic(TsscTopic topic) throws NullTopicException, NotEnoughGroupsException, NotEnoughSprintsException;
	public TsscTopic editTopic(TsscTopic topic) throws NullTopicException, NotExistingTopic, NotEnoughSprintsException, NotEnoughGroupsException;

}
