package co.edu.icesi.fi.tics.tssc.repositories;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface TopicRepository {
	
	public TsscTopic saveTopic(TsscTopic topic);
	public TsscTopic editTopic(TsscTopic topic);
	public TsscTopic getTopic(Long id);

}
