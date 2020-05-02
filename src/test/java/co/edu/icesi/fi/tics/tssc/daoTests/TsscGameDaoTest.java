package co.edu.icesi.fi.tics.tssc.daoTests;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.fi.tics.tssc.TallerThymeleafApplication;
import co.edu.icesi.fi.tics.tssc.dao.TsscGameDao;
import co.edu.icesi.fi.tics.tssc.dao.TsscStoryDao;
import co.edu.icesi.fi.tics.tssc.dao.TsscTopicDao;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TallerThymeleafApplication.class)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class TsscGameDaoTest {
	
	
	
	
	//// FALTA QUE FUNCIOJNE EL DESCRIPTION
	@Autowired
	private TsscGameDao gameDao;
	@Autowired
	private TsscStoryDao storyDao;
	
	@Autowired
	private TsscTopicDao topicDao;
	

	
	private TsscGame g1, g2, g3, g4, g5;
	private TsscTopic t1, t2; 
	private TsscStory s1, s2, s3, s4,s5,s6,s7,s8,s9,s10;
	

	
	@Test
	@Order(1)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Add game")
	public void test1()
	{
		setUp();
		topicDao.save(t1);
		topicDao.save(t2);

		assertEquals(0,gameDao.findAll().size());
		gameDao.save(g1);
		gameDao.save(g2);
		gameDao.save(g3);
		gameDao.save(g4);
		gameDao.save(g5);

		assertEquals(5,gameDao.findAll().size());
			
	}
	
	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Order(2)
	@DisplayName("Update game")
	public void test2()
	{
		TsscGame g = gameDao.findById(1);
		String name = "Nuevo juego";
		g.setName(name);
		gameDao.update(g);
		assertEquals(name, gameDao.findById(1).getName());

	}
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find by id")
	public void test3()
	{
		List<TsscGame> games = gameDao.findAll();
		assertEquals(games.get(0), gameDao.findById(1));
	}
	
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find by name")
	public void test4()
	{
		String name = "Juego 2";
		assertEquals(name, gameDao.findByName(name).get(0).getName());
	}
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find by description")
	public void test5()
	{
		String description = "Este es el primer juego creado";
		assertEquals(description, gameDao.findByDescription(description).get(0).getDescripcion());
	}


	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find games by date range")
	public void test6()
	{
		List<TsscGame> resp = gameDao.findByDateRange(LocalDate.of(2020,11,01), LocalDate.of(2020, 12,31));
		assertEquals(2, resp.size());
		assertEquals("Juego 3", resp.get(0).getName());
		assertEquals("Juego 5", resp.get(1).getName());

	}
	


	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find games by time range")
	public void test7()
	{
		List<TsscGame> resp = gameDao.findByDateAndTimeRange(LocalDate.of(2020, 10, 20),LocalTime.of(7,10), LocalTime.of(9,20));
		assertEquals(1, resp.size());
		assertEquals("Juego 4", resp.get(0).getName());
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find topics by date")
	public void test8()
	{//Trae en orden DESC 
		
		List<Object[]> result = gameDao.findTopicsByDate(LocalDate.of(2020, 10, 20));
			
			assertEquals(2,result.size());
			TsscTopic t = (TsscTopic) result.get(0)[0];
			assertEquals("Tema 1",t.getName());

			assertEquals((long)2,result.get(0)[1]);
			TsscTopic t2 = (TsscTopic) result.get(1)[0];

			assertEquals("Tema 2",t2.getName());
			assertEquals((long)1,result.get(1)[1]);
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find topics and its games by date in order by scheduledTime")
	public void test9()
	{//Trae en orden DESC 
		
		List<Object[]> result = gameDao.findTopicsByDate2(LocalDate.of(2020, 10, 20));

			
		
			assertEquals(3,result.size());
			//Primero en hora (8)
			TsscTopic t1 = (TsscTopic) result.get(0)[0];
			assertEquals("Tema 2",t1.getName());
			TsscGame g1 = (TsscGame) result.get(0)[1];
			assertEquals("Juego 4",g1.getName());
			
			//Segundo en hora
			TsscTopic t2 = (TsscTopic) result.get(1)[0];
			assertEquals("Tema 1",t2.getName());
			TsscGame g2 = (TsscGame) result.get(1)[1];
			assertEquals("Nuevo juego",g2.getName());
			//Tercero en hora
			TsscTopic t3 = (TsscTopic) result.get(2)[0];
			assertEquals("Tema 1",t3.getName());
			TsscGame g3 = (TsscGame) result.get(2)[1];
			assertEquals("Juego 2",g3.getName());

	}



	@Test
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find games by stories or timecontrols")
	public void test10()
	{

		//Cumple la fecha, tiene historias pero son menos de 10 historias
		assertEquals(4, gameDao.findById(3).getTsscStories().size());
		assertEquals("Juego 3",gameDao.findByNoStoriesNoTimeControls(LocalDate.of(2020, 12,3)).get(0).getName());
		//Cumple la fecha, tiene 10 historias pero no tiene ningun cronometro especificado 
		assertEquals(10, gameDao.findById(5).getTsscStories().size());
		assertEquals("Juego 5",gameDao.findByNoStoriesNoTimeControls(LocalDate.of(2020, 12,3)).get(1).getName());
	
	}

	
	@Test
	@Order(4)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Delete game")
	public void test11()
	{
		assertEquals(5,gameDao.findAll().size());
		gameDao.delete(gameDao.findById(1));
		assertEquals(4,gameDao.findAll().size());
		
	}
	
	
	
	//------------------------------------------------------------- SET UPS---------------------------------------
	
	
	
	
	public void setUp()
	{
		

		LocalDate d1 = LocalDate.of(2020, 10, 20);
		LocalDate d2 = LocalDate.of(2020, 12, 3);
		t1 = new TsscTopic(); 
		t1.setName("Tema 1");
		t2 = new TsscTopic();
		t2.setName("Tema 2");
		g1 = new TsscGame(); 
		g1.setDescription("Este es el primer juego creado");
		g1.setTsscTopic(t1);
		g1.setName("Juego 1");
		g2 = new TsscGame(); 
		g2.setTsscTopic(t1);
		g2.setName("Juego 2");
		g3 = new TsscGame(); 
		g3.setTsscTopic(t1);
		g3.setName("Juego 3");
		g4 = new TsscGame(); 
		g4.setTsscTopic(t2);
		g4.setName("Juego 4");
		g5 = new TsscGame();
		g5.setName("Juego 5");
		g5.setScheduledDate(d2);
		g1.setScheduledDate(d1);
		g1.setScheduledTime(LocalTime.of(10, 20));
		g2.setScheduledDate(d1);
		g3.setScheduledDate(d2);
		g4.setScheduledDate(d1);
		g4.setScheduledTime(LocalTime.of(8, 20));
		g3.setScheduledTime(LocalTime.of(12, 20));
		g2.setScheduledTime(LocalTime.of(11, 20));
		
		s1 = new TsscStory();
		s2 = new TsscStory();
		s3 = new TsscStory();
		s4 = new TsscStory();
		
	}
	
	public void setUp2()
	{
		s1 = new TsscStory();
		s2 = new TsscStory();
		s3 = new TsscStory();
		s4 = new TsscStory();
		s5 = new TsscStory();
		s6 = new TsscStory();
		s7 = new TsscStory();
		s8 = new TsscStory();
		s9 = new TsscStory();
		s10 = new TsscStory();	
	}
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUp4()
	{
		setUp2();
		s1.setTsscGame(gameDao.findById(5));
		s2.setTsscGame(gameDao.findById(5));
		s3.setTsscGame(gameDao.findById(5));
		s4.setTsscGame(gameDao.findById(5));
		s5.setTsscGame(gameDao.findById(5));
		s6.setTsscGame(gameDao.findById(5));
		s7.setTsscGame(gameDao.findById(5));
		s8.setTsscGame(gameDao.findById(5));	
		s9.setTsscGame(gameDao.findById(5));
		s10.setTsscGame(gameDao.findById(5));

		storyDao.save(s1);
		storyDao.save(s2);
		storyDao.save(s3);
		storyDao.save(s4);
		storyDao.save(s5);
		storyDao.save(s6);
		storyDao.save(s7);
		storyDao.save(s8);	
		storyDao.save(s9);
		storyDao.save(s10);
	}
	
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setUp3()
	{
		setUp();
		s1.setTsscGame(gameDao.findById(3));
		s2.setTsscGame(gameDao.findById(3));
		s3.setTsscGame(gameDao.findById(3));
		s4.setTsscGame(gameDao.findById(3));

		storyDao.save(s1);
		storyDao.save(s2);
		storyDao.save(s3);
		storyDao.save(s4);
	}
	
	
}

