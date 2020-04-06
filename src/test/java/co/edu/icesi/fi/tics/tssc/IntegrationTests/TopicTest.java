package co.edu.icesi.fi.tics.tssc.IntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TopicTest {
	
	private TsscTopic topic;
	
	private TopicServiceImpl topicService;
	
	private TopicRepository topicRepository;
	
	@Autowired
	public TopicTest(TopicServiceImpl topicService, TopicRepository topicRepository)
	{
		this.topicService = topicService;
		this.topicRepository = topicRepository;
		topic = new TsscTopic();
		topic.setDefaultSprints(10);
		topic.setDefaultGroups(8);
	}
	
	@Test
	@DisplayName("Save topic")
	public void saveTest()
	{
		try {
			topicService.saveTopic(topic);
		} catch (NullTopicException | NotEnoughGroupsException | NotEnoughSprintsException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		assertEquals(topic, topicRepository.getTopic(topic.getId()));
		
	}
	
	@Test
	@DisplayName("Edit topic")
	public void editTest()
	{
		try {
			topicService.saveTopic(topic);
			topic.setName("Topic xyz");
			topicService.editTopic(topic);
		} catch (NullTopicException | NotEnoughGroupsException | NotEnoughSprintsException | NotExistingTopic e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		assertEquals(topic, topicRepository.getTopic(topic.getId()));
		
	}

}
