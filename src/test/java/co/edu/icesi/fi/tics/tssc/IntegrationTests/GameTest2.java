package co.edu.icesi.fi.tics.tssc.IntegrationTests;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.GameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;
import co.edu.icesi.fi.tics.tssc.services.GameService;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GameTest2 {

	private GameServiceImpl gameService;
	
	private GameRepository gameRepository;
	
	private TopicRepository topicRepository;
	
	private TsscGame game;
	private TsscTopic topic;
	
	
	
	@Autowired
	public GameTest2(GameServiceImpl gameService, GameRepository gameRepository, TopicRepository topicRepository)
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
		ArrayList<TsscStory> stories = new ArrayList<TsscStory>();
		TsscStory story1 = new TsscStory();
		story1.setBusinessValue(new BigDecimal("10"));
		TsscStory story2 = new TsscStory();
		story1.setBusinessValue(new BigDecimal("8"));
		
		stories.add(story1);
		stories.add(story2);
		topic.setTsscStories(stories);
		
		ArrayList<TsscTimecontrol> timeControls = new ArrayList<TsscTimecontrol>();
		
		TsscTimecontrol t1 = new TsscTimecontrol();
		t1.setAutostart("a");
		TsscTimecontrol t2 = new TsscTimecontrol();
		t2.setAutostart("a");
		
		timeControls.add(t1);
		timeControls.add(t2);
		
		topic.setTsscTimecontrols(timeControls);

		topicRepository.saveTopic(topic);
		
		
	}
	
	
	@Test
	@DisplayName("Save game with out topic")
	public void saveTest1()
	{
		
		try {
			gameService.saveGame2(game, topic);
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullGameException | NotExistingTopic | NullTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertSame(game,gameRepository.getGame(game.getId()));

		assertNotSame(topic.getTsscStories(), gameRepository.getGame(game.getId()).getTsscStories());
		assertEquals(topic.getTsscStories(), gameRepository.getGame(game.getId()).getTsscStories());
		assertNotSame(topic.getTsscTimecontrols(), gameRepository.getGame(game.getId()).getTsscTimecontrols());
		assertEquals(topic.getTsscTimecontrols(), gameRepository.getGame(game.getId()).getTsscTimecontrols());


		

	}

	
	

}
