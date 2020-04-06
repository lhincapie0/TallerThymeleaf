package co.edu.icesi.fi.tics.tssc.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@Repository
public class TopicRepositoryImpl implements TopicRepository{

	private Map<Long, TsscTopic> topics;	

	public TopicRepositoryImpl()
	{
		topics = new HashMap<Long, TsscTopic>();
	}
	
	@Override
	public TsscTopic saveTopic(TsscTopic topic) {
		topics.put(topic.getId(), topic);
		return topic;
	}

	@Override
	public TsscTopic editTopic(TsscTopic topic) {
		TsscTopic existingtopic = topics.get(topic.getId());
		topics.replace(topic.getId(), topic, existingtopic);
		return topic;
	
	}

	@Override
	public TsscTopic getTopic(Long id) {
		TsscTopic topic = topics.get(id);
		return topic;
	}

}
