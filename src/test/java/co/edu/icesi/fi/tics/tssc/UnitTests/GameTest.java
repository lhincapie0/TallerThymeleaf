package co.edu.icesi.fi.tics.tssc.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.hibernate.criterion.NullExpression;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.GameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

public class GameTest {

	
	
	@Mock
	private GameRepository mockGame;
	

	@Mock
	private TopicRepository mockTopic; 
	
	@InjectMocks
	private GameServiceImpl gameService;
	
	
	private TsscGame game;
	
	@InjectMocks
	private TopicServiceImpl topicService;
	
	private TsscTopic topic;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		game = new TsscGame();
		game.setId(123);
		game.setNGroups(2);
		game.setNSprints(2);
		
		topic = new TsscTopic();
		topic.setId(123);
		
	}		
	
	@Test
	@DisplayName("Null game")
	public void saveTest1()
	{
		assertThrows(NullGameException.class, () -> gameService.saveGame(null,null));
		verifyZeroInteractions(mockGame);
	}
	
	
	@Test
	@DisplayName("Not enough groups")
	public void saveTest2()
	{
		game.setNGroups(0);
		assertThrows(NotEnoughGroupsException.class, ()-> gameService.saveGame(game,null));
		verifyZeroInteractions(mockGame);
	}
	
	
	@Test
	@DisplayName("Not enough sprints")
	public void saveTest3()
	{
		game.setNSprints(0);
		assertThrows(NotEnoughSprintsException.class, ()-> gameService.saveGame(game,null));
		verifyZeroInteractions(mockGame);
	}
	
	@Test
	@DisplayName("An unexisting topic is added")
	public void saveTest4()
	{
		when(mockTopic.getTopic((long) 123.0)).thenReturn(null);
		assertThrows(NotExistingTopic.class, () -> gameService.saveGame(game, topic));
		verifyZeroInteractions(mockGame);

	}
		
	
	@Test
	@DisplayName("An existing topic is added - Successful")
	public void saveTest5()
	{
		when(mockTopic.getTopic((long) 123.0)).thenReturn(topic);
		try {
			assertEquals(game, gameService.saveGame(game, topic));
			verify(mockGame, times(1)).saveGame(game);
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullGameException | NotExistingTopic e) {
		}
	}
	
	@Test
	@DisplayName("Successful addition with no topic")
	public void saveTest6()
	{
		try {
			assertEquals(game, gameService.saveGame(game, null));
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullGameException | NotExistingTopic e) {
			e.printStackTrace();
		}
		verify(mockGame, times(1)).saveGame(game);
	}
	
	@Test
	@DisplayName("Not existing game for editting")
	public void editTest1()
	{
		when(mockGame.editGame(game)).thenReturn(null);
		assertThrows(NotExistingGameException.class, ()-> gameService.editGame(game));
		//It is 0 because it tried to interact but it didn´t return anything
		verify(mockGame, times(0)).editGame(game);
	}

	@Test
	@DisplayName("Null game for editting")
	public void editTest2()
	{
		assertThrows(NullGameException.class, () -> gameService.editGame(null));
		verifyZeroInteractions(mockGame);
	}
	
	@Test
	@DisplayName("Not enough groups edited")
	public void editTest3()
	{
		game.setNGroups(0);
		when(mockGame.getGame(game.getId())).thenReturn(game);
		assertThrows(NotEnoughGroupsException.class, () -> gameService.editGame(game));
		verify(mockGame, times(0)).editGame(game);

	}
	

	@Test
	@DisplayName("Not enough sprints edited")
	public void editTest4()
	{
		game.setNSprints(0);
		when(mockGame.getGame(game.getId())).thenReturn(game);
		assertThrows(NotEnoughSprintsException.class, () -> gameService.editGame(game));
		verify(mockGame, times(0)).editGame(game);
	}
	
	@Test
	@DisplayName("An unexisting topic is added in the edition")
	public void editTest5()
	{
		when(mockGame.getGame(game.getId())).thenReturn(game);
		when(mockTopic.getTopic((long) 123.0)).thenReturn(null);
		game.setTsscTopic(topic);
		assertThrows(NotExistingTopic.class, () -> gameService.editGame(game));
		verify(mockGame, times(0)).editGame(game);

	}
	
	
	@Test
	@DisplayName("An existing topic is added in the edition - Successsful")
	public void editTest6()
	{
		when(mockGame.getGame(game.getId())).thenReturn(game);
		when(mockTopic.getTopic((long) 123.0)).thenReturn(topic);
		game.setTsscTopic(topic);
		try {
			assertEquals(game, gameService.editGame(game));
		} catch (NotExistingGameException | NullGameException | NotEnoughGroupsException | NotEnoughSprintsException e) {
		} catch (NotExistingTopic e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	@DisplayName("Successful editting")
	public void editTest7()
	{
		when(mockGame.getGame(game.getId())).thenReturn(game);
		game.setName("Game 33");
		try {
			assertEquals(game, gameService.editGame(game));
		} catch (NotExistingGameException | NullGameException | NotEnoughGroupsException | NotEnoughSprintsException e) {
		} catch (NotExistingTopic e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

	

}



