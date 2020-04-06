package co.edu.icesi.fi.tics.tssc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;

@Service
public class TopicServiceImpl implements TopicService{
	
	@Autowired
	private TopicRepository topicRepository;

	@Override
	public TsscTopic saveTopic(TsscTopic topic) throws NullTopicException, NotEnoughGroupsException, NotEnoughSprintsException{
		if(topic != null)
		{
			if(topic.getDefaultGroups()>0)
			{
				if(topic.getDefaultSprints()>0)
				{
					topicRepository.saveTopic(topic);
					return topic;
				}else throw new NotEnoughSprintsException();
			}else throw new NotEnoughGroupsException();
				
		}else throw new NullTopicException();
			
	}

	@Override
	public TsscTopic editTopic(TsscTopic topic) throws NullTopicException, NotExistingTopic, NotEnoughSprintsException, NotEnoughGroupsException{
		if(topic != null)
		{
			if(topicRepository.getTopic(topic.getId()) != null)
			{
				if(topic.getDefaultGroups()>0)
				{
					if(topic.getDefaultSprints()>0)
					{
						topicRepository.editTopic(topic);
						
					}else throw new NotEnoughSprintsException();
				}else throw new NotEnoughGroupsException();
				
				return topic;
			}else throw new NotExistingTopic();
		}else throw new NullTopicException();
	}

}
