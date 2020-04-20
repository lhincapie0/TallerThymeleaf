package co.edu.icesi.fi.tics.tssc.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.hibernate.criterion.NullExpression;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

public class TopicTest {

	
	@Mock
	private TopicRepository mock;
	
	@InjectMocks
	private TopicServiceImpl topicService;
	private TsscTopic topic;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		topic = new TsscTopic();
		topic.setId(11111);
		topic.setName("Tema 1");
		topic.setGroupPrefix("t1");

		
		
	}
	
	@Test
	@DisplayName("Null topic")
	public void saveTest1()
	{
		assertThrows(NullTopicException.class, ()-> topicService.saveTopic(null));
		verifyZeroInteractions(mock);

	}
	
	@Test
	@DisplayName("Not enough groups")
	public void saveTest2()
	{
	
		topic.setDefaultSprints(4);
		assertThrows(NotEnoughGroupsException.class, ()-> topicService.saveTopic(topic));
		verifyZeroInteractions(mock);

	}
	
	@Test
	@DisplayName("Not enough sprints")
	public void saveTest3()
	{
	
		topic.setDefaultGroups(4);
		assertThrows(NotEnoughSprintsException.class, ()-> topicService.saveTopic(topic));
		verifyZeroInteractions(mock);
		//verify(mock,times(0)).saveTopic(topic);
	}
	
	@Test
	@DisplayName("Successful addition")
	public void saveTest4()
	{
		topic.setDefaultGroups(4);
		topic.setDefaultSprints(4);
		when(mock.save(topic)).thenReturn(topic);

		try {
			
			assertEquals(topic, topicService.saveTopic(topic));
			
		} catch (NullTopicException e) {
		} catch (NotEnoughGroupsException e) {
		} catch (NotEnoughSprintsException e) {
		}
		verify(mock, times(1)).save(topic);
	}
	
	@Test 
	@DisplayName("Not existing topic for editting")
	public void editTest1() {
		when(mock.save(topic)).thenReturn(null);
		assertThrows(NotExistingTopic.class, ()-> topicService.editTopic(topic));
		//It is 0 because it tried to interact but it didn´t return anything
		verify(mock, times(0)).save(topic);
		

	}
	
	
	@Test 
	@DisplayName("Null topic for editting")
	public void editTest2() {
		assertThrows(NullTopicException.class, ()-> topicService.editTopic(null));
		verifyZeroInteractions(mock);
	}
	
	@Test
	@DisplayName("Not enough games edited")
	public void editTest3()
	{
		when(mock.existsById(topic.getId())).thenReturn(true);
		topic.setName("Edited topic");
		topic.setDefaultGroups(0);
		assertThrows(NotEnoughGroupsException.class, ()-> topicService.editTopic(topic));
		verify(mock, times(0)).save(topic);
	}
	
	@Test
	@DisplayName("Not enough springs edited")
	public void editTest4()
	{
		{
			when(mock.existsById(topic.getId())).thenReturn(true);

			topic.setName("Edited topic");
			topic.setDefaultSprints(0);
			topic.setDefaultGroups(2);
			assertThrows(NotEnoughSprintsException.class, ()-> topicService.editTopic(topic));
			verify(mock, times(0)).save(topic);
		}
	}
	
	
	@Test 
	@DisplayName("Successful editting")
	public void editTest5() {
		topic.setDefaultSprints(4);
		topic.setDefaultGroups(3);

		try {
			topicService.saveTopic(topic);
			topic.setName("Edited topic");
			topic.setDefaultGroups(3);
			topic.setDefaultSprints(2);
		
			assertEquals(topic, topicService.editTopic(topic));
			verify(mock, times(1)).save(topic);

		} catch (NullTopicException |NotEnoughGroupsException | NotEnoughSprintsException | NotExistingTopic e ) {
			
		}
	}
	
}
