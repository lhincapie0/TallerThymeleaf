package co.edu.icesi.fi.tics.tssc.IntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.criteria.Order;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.GameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;
import co.edu.icesi.fi.tics.tssc.services.GameService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GameTest {

	private GameService gameService;
	
	private GameRepository gameRepository;
	
	private TopicRepository topicRepository;
	
	private TsscGame game;
	private TsscTopic topic;
	
	
	
	@Autowired
	public GameTest(GameService gameService, GameRepository gameRepository, TopicRepository topicRepository)
	{
		this.gameService = gameService;
		this.gameRepository = gameRepository;
		this.topicRepository = topicRepository;
		game = new TsscGame();
		game.setId(123);
		game.setNGroups(2);
		game.setNSprints(2);
		
		topic = new TsscTopic();
		topic.setId(123);
	}
	
	
	@Test
	@DisplayName("Save game with out topic")
	public void saveTest1()
	{
		
		try {
			gameService.saveGame(game, null);
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullGameException | NotExistingTopic e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(game,gameRepository.getGame(game.getId()));

	}
	@Test
	@DisplayName("Save game with topic")
	public void saveTest2()
	{
		topicRepository.saveTopic(topic);
		
		try {
			gameService.saveGame(game, topic);
			game.setTsscTopic(topic);
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullGameException | NotExistingTopic e) {
			e.printStackTrace();
		}
		assertEquals(game,gameRepository.getGame(game.getId()));

	}
	
	
	@Test
	@DisplayName("Edit the game")
	public void editTest1()
	{
		try {
			gameService.saveGame(game, null);
			game.setNGroups(10);
			game.setNSprints(20);
			gameService.editGame(game);
			
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullGameException | NotExistingTopic | NotExistingGameException e) {
			e.printStackTrace();
		}
		assertEquals(game, gameRepository.getGame(game.getId()));

		
	}
	@Test
	@DisplayName("Edit the game and the topic associated")
	public void editTest2()
	{
		try {
			topicRepository.saveTopic(topic);
			gameService.saveGame(game, topic);
			game.setNGroups(10);
			game.setNSprints(20);
			topic.setDescription("Topic 2");
			game.setTsscTopic(topic);
			gameService.editGame(game);
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullGameException | NotExistingTopic | NotExistingGameException e) {
			e.printStackTrace();
		}
		assertEquals(game, gameRepository.getGame(game.getId()));

		
	}
	
	

}
