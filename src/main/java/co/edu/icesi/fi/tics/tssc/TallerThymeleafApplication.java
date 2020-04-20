package co.edu.icesi.fi.tics.tssc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughGroupsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotEnoughSprintsException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NotExistingTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.NullAdminException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullGameException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullStoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.NullTopicException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.AdminService;
import co.edu.icesi.fi.tics.tssc.services.AdminServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.StoryServiceImpl;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;


@SpringBootApplication
public class TallerThymeleafApplication {

	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	public static void main(String[] args) throws NullTopicException, NotEnoughGroupsException, NotEnoughSprintsException, NullGameException, NotExistingTopic, NullAdminException, NullStoryException, BusinessValueException, InitialSprintException, PriorityException, NotExistingGameException {

		ConfigurableApplicationContext c = SpringApplication.run(TallerThymeleafApplication.class, args);
		AdminServiceImpl adminService = c.getBean(AdminServiceImpl.class);
		TopicServiceImpl topicService = c.getBean(TopicServiceImpl.class);
		GameServiceImpl gameService = c.getBean(GameServiceImpl.class);
		StoryServiceImpl storyService = c.getBean(StoryServiceImpl.class);
		
		
	//CREACIÓN DE USUARIOS
		
		TsscAdmin admin1 = new TsscAdmin();
		admin1.setPassword("{noop}123");
		admin1.setUsername("Super Administrador");
		admin1.setUser("Super Administrador");
		admin1.setSuperAdmin("super");
		
		adminService.saveAdmin(admin1);
		
		
		TsscAdmin admin2 = new TsscAdmin();
		admin2.setPassword("{noop}123");
		admin2.setUsername("Administrador");
		admin2.setUser("Administrador");
		admin2.setSuperAdmin("admin");
		
		adminService.saveAdmin(admin2);
		TsscAdmin user = new TsscAdmin();
		user.setPassword("{noop}123");
		user.setUsername("Usuario");
		user.setUser("Usuario");
		user.setSuperAdmin("user");
		adminService.saveAdmin(user);
		
		//Creación de temas
		TsscTopic topic = new TsscTopic();
		topic.setDefaultGroups(2);
		topic.setDefaultSprints(3);
		topic.setName("TSC2");
		topic.setDescription("Tema para est. ing");
		topicService.saveTopic(topic);
		
		TsscTopic topic2 = new TsscTopic();
		topic2.setDefaultGroups(2);
		topic2.setDefaultSprints(3);
		topic2.setName("TS Biol");
		topic2.setDescription("Tema para est. biol");
		topicService.saveTopic(topic);
		
		
		//Creación de juegos
		TsscGame game = new TsscGame();
		game.setName("Completa P-12");
		game.setNGroups(4);
		game.setNSprints(3);
		Date date = new Date(2323223232L);
		game.setScheduledDate(convertToLocalDateViaInstant(date));
		gameService.saveGame(game, topic);
		TsscGame game2 = new TsscGame();				
		game2.setName("Black Jack");
		game2.setNGroups(10);
		game2.setNSprints(3);
		gameService.saveGame(game2, topic);
		
		
		
		//Creación de historias
		TsscStory story = new TsscStory();
		story.setDescription("Historia 1 CP12");
		story.setBusinessValue(new BigDecimal("10000"));
		story.setPriority(new BigDecimal("100"));
		story.setInitialSprint(new BigDecimal("300"));
		
		TsscStory story2 = new TsscStory();
		story2.setDescription("Historia 2 CP12");
		story2.setBusinessValue(new BigDecimal("40000"));
		story2.setPriority(new BigDecimal("300"));
		story2.setInitialSprint(new BigDecimal("10000"));
		
		storyService.saveStory(story,game);
		storyService.saveStory(story2, game);
		
		
	
		


	}
	
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}


}
