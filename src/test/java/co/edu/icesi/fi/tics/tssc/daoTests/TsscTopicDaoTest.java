package co.edu.icesi.fi.tics.tssc.daoTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.fi.tics.tssc.TallerThymeleafApplication;
import co.edu.icesi.fi.tics.tssc.dao.TsscTopicDao;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.GameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.TopicRepository;
import co.edu.icesi.fi.tics.tssc.services.GameService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TallerThymeleafApplication.class)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class TsscTopicDaoTest {
	
	@Autowired
	private TsscTopicDao topicDao;
	private TsscTopic topicTest1;
	private TsscTopic topicTest2;
	
	
	public void setUp()
	{
		topicTest1 = new TsscTopic();
		topicTest1.setName("Tema 1");
		topicTest1.setDescription("Primer tema creado");	
		topicTest2 = new TsscTopic();
		topicTest2.setName("Tema 2");
		topicTest2.setDefaultGroups(6);
		topicTest2.setDescription("Segundo tema creado");
	}
	
	@Test
	@Order(1)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Add topic")
	public void test1()
	{
		setUp();
		assertEquals(0,topicDao.findAll().size());
		topicDao.save(topicTest1);
		topicDao.save(topicTest2);
		assertEquals(2,topicDao.findAll().size());
	}
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Update topic")
	public void test2()
	{
		TsscTopic t = topicDao.findById(2);
		String name = "Topic 10";
		t.setName(name);
		topicDao.update(t);
		assertEquals(name, topicDao.findById(2).getName());
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find by id")
	public void test3()
	{
		List<TsscTopic> topics = topicDao.findAll();
		assertEquals(topics.get(0), topicDao.findById(1));
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find by name")
	public void test4()
	{
		assertEquals("Tema 1", topicDao.findByName("Tema 1").get(0).getName());
	}
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find by description")
	public void test5()
	{
		List<TsscTopic> topics = topicDao.findAll();
		String description = "Segundo tema creado";
		assertEquals(description, topicDao.findByDescription(description).get(0).getDescription());
	}
	
	
	@Test
	@Order(3)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Delete topic")
	public void test6()
	{
		assertEquals(2,topicDao.findAll().size());
		List<TsscTopic> topics = topicDao.findAll();
		topicDao.delete(topics.get(0));
		assertEquals(1,topicDao.findAll().size());
	}
	
	
}

