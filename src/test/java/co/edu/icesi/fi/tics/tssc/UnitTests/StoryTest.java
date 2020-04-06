package co.edu.icesi.fi.tics.tssc.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.criterion.NullExpression;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.GameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.StoryRepository;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.StoryServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

public class StoryTest {

	
	
	@Mock
	private GameRepository mockGame;

	@Mock
	private StoryRepository mockStory; 
	
	@InjectMocks
	private GameServiceImpl gameService;
	
	
	
	@InjectMocks
	private TopicServiceImpl topicService;
	
	@InjectMocks
	private StoryServiceImpl storyService;
	
	private TsscGame game;

	private TsscStory story;
	
	@BeforeEach
	public void init()
	{
	
		MockitoAnnotations.initMocks(this);
		story = new TsscStory();
		story.setBusinessValue(new BigDecimal("10000"));
		story.setPriority(new BigDecimal("10"));
		story.setInitialSprint(new BigDecimal("5"));
		
		game = new TsscGame();
		game.setId(12);
		
		story.setTsscGame(game);
		
	}		
	
	
	@Test
	@DisplayName("Null game")
	public void saveTest1()
	{
		assertThrows(NullStoryException.class, () -> storyService.saveStory(null,null));
		verifyZeroInteractions(mockStory);
	}
	
	
	@Test
	@DisplayName("Not enough business value")
	public void saveTest2()
	{
		story.setBusinessValue(new BigDecimal("0"));
		assertThrows(BusinessValueException.class, () -> storyService.saveStory(story, null));
		verifyZeroInteractions(mockStory);
	}
	
	@Test
	@DisplayName("Not enough value for initial sprint")
	public void saveTest3()
	{
		story.setInitialSprint(new BigDecimal("0"));
		assertThrows(InitialSprintException.class, () -> storyService.saveStory(story,null));
		verifyZeroInteractions(mockStory);
	}
	
	@Test
	@DisplayName("Not enough priority")
	public void saveTest4()
	{
		story.setPriority(new BigDecimal("0"));
		assertThrows(PriorityException.class, () -> storyService.saveStory(story,null));
		verifyZeroInteractions(mockStory);
	}
		
	@Test
	@DisplayName("Null game associated")
	public void saveTest5()
	{
		assertThrows(NullGameException.class, () -> storyService.saveStory(story,null));
		verifyZeroInteractions(mockStory);
	}
	
	@Test
	@DisplayName("No existing game associated")
	public void saveTest6()
	{
		when(mockGame.getGame(game.getId())).thenReturn(null);
		assertThrows(NotExistingGameException.class, () -> storyService.saveStory(story,game));
		verifyZeroInteractions(mockStory);
	}
	
	@Test
	@DisplayName("Successful addition")
	public void saveTest7()
	{
		when(mockGame.getGame(game.getId())).thenReturn(game);
		try {
			assertEquals(story, storyService.saveStory(story, game));
			verify(mockStory, times(1)).saveStory(story);


		} catch (NullStoryException | BusinessValueException | InitialSprintException | PriorityException
				| NullGameException | NotExistingGameException e) {
		}

	}
	
	@Test
	@DisplayName("Null story for editting")
	public void editTest1()
	{
		assertThrows(NullStoryException.class, () -> storyService.editStory(null));
		verifyZeroInteractions(mockStory);
	}
	
	@Test
	@DisplayName("Not existing story for editting")
	public void editTest2()
	{
		when(mockStory.getStory(story.getId())).thenReturn(null);
		assertThrows(NotExistingStory.class, () -> storyService.editStory(story));
		verify(mockStory, times(0)).editStory(story);

	}
	
	@Test
	@DisplayName("Not enough business value edited")
	public void editTest3()
	{
		when(mockStory.getStory(story.getId())).thenReturn(story);
		story.setBusinessValue(new BigDecimal("0"));
		assertThrows(BusinessValueException.class, () -> storyService.editStory(story));
		verify(mockStory, times(0)).editStory(story);

	}
	
	
	@Test
	@DisplayName("Not enough value for initial sprint")
	public void editTest4()
	{
		when(mockStory.getStory(story.getId())).thenReturn(story);
		story.setInitialSprint(new BigDecimal("0"));
		assertThrows(InitialSprintException.class, () -> storyService.editStory(story));
		verify(mockStory, times(0)).editStory(story);

	}
	
	@Test
	@DisplayName("Not enough priority edited")
	public void editTest5()
	{
		when(mockStory.getStory(story.getId())).thenReturn(story);
		story.setPriority(new BigDecimal("0"));
		assertThrows(PriorityException.class, () -> storyService.editStory(story));
		verify(mockStory, times(0)).editStory(story);
	}
	
	@Test
	@DisplayName("Not existing game associated")
	public void editTest6()
	{
		TsscGame game1 = new TsscGame();
		game1.setId(20);
		story.setTsscGame(game1);
		when(mockStory.getStory(story.getId())).thenReturn(story);
		when(mockGame.getGame(game.getId())).thenReturn(null);
		assertThrows(NotExistingGameException.class, () -> storyService.editStory(story));
		verify(mockStory, times(0)).editStory(story);
	}
	
	@Test
	@DisplayName("Successful editting")
	public void editTest7()
	{
		when(mockStory.getStory(story.getId())).thenReturn(story);
		when(mockGame.getGame(game.getId())).thenReturn(game);
		try {
			assertEquals(story, storyService.editStory(story));
		} catch (NullStoryException | NotExistingStory | BusinessValueException | PriorityException
				| InitialSprintException | NotExistingGameException e) {
		}

	}
	
	
}
