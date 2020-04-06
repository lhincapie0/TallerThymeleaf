package co.edu.icesi.fi.tics.tssc.IntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingStory;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.repositories.GameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.StoryRepository;
import co.edu.icesi.fi.tics.tssc.services.StoryServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoryTest {
	
	private StoryServiceImpl storyService;
	
	private StoryRepository storyRepository;
	
	private GameRepository gameRepository;
	
	private TsscGame game;
	private TsscStory story;
	
	@Autowired
	public StoryTest(StoryServiceImpl storyService, StoryRepository storyRepository, GameRepository gameRepository)
	{
		this.storyService = storyService;
		this.storyRepository = storyRepository;
		this.gameRepository = gameRepository;
		
	
		game = new TsscGame();
		gameRepository.saveGame(game);
		
		story = new TsscStory();
		story.setInitialSprint(new BigDecimal("10"));
		story.setBusinessValue(new BigDecimal("10000"));
		story.setPriority(new BigDecimal("2"));
		story.setTsscGame(game);
		
		
	}
	
	@Test
	@DisplayName("Save story")
	@org.junit.jupiter.api.Order(1)
	public void saveTest1()
	{
		try {
			storyService.saveStory(story, game);
		} catch (NullStoryException | BusinessValueException | InitialSprintException | PriorityException
				| NullGameException | NotExistingGameException e) {
			e.printStackTrace();
		}
		assertEquals(story, storyRepository.getStory(story.getId()));

	}
	
	
	@Test
	@DisplayName("Edit story")
	@org.junit.jupiter.api.Order(2)
	public void saveTest2()
	{
		
		try {
			storyService.saveStory(story, game);
			story.setShortDescription("New story for the xyz game");
			story.getTsscGame().setName("xyz game");
			storyService.editStory(story);
		} catch (NullStoryException | NotExistingStory | BusinessValueException | PriorityException
				| InitialSprintException | NotExistingGameException | NullGameException e) {
			e.printStackTrace();
		}
		assertEquals(story,storyRepository.getStory(story.getId()));
	}

}
