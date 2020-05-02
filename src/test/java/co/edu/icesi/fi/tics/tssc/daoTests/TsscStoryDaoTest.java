package co.edu.icesi.fi.tics.tssc.daoTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.fi.tics.tssc.TallerThymeleafApplication;
import co.edu.icesi.fi.tics.tssc.dao.TsscStoryDao;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TallerThymeleafApplication.class)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class TsscStoryDaoTest {
	
	@Autowired
	private TsscStoryDao storyDao;
	
	private TsscStory story1;
	private TsscStory story2;
	
	
	public void setUp()
	{
		story1 = new TsscStory();
		story1.setDescription("Historia test 1");
		story2 = new TsscStory();
		story2.setDescription("Historia test 2");
	}
	
	@Test
	@Order(1)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Add Story")
	public void test1()
	{
		setUp();
		assertEquals(0,storyDao.findAll().size());
		storyDao.save(story1);
		storyDao.save(story2);
		assertEquals(2,storyDao.findAll().size());
	}
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Update topic")
	public void test2()
	{
		TsscStory t = storyDao.findById(2);
		String description = "Historia vieja";
		t.setDescription(description);
		storyDao.update(t);
		assertEquals(description, storyDao.findById(2).getDescription());
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find by id")
	public void test3()
	{
		List<TsscStory> stories = storyDao.findAll();
		assertEquals(stories.get(0), storyDao.findById(1));
	}
	

	
	@Test
	@Order(3)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Delete topic")
	public void test6()
	{
		assertEquals(2,storyDao.findAll().size());
		storyDao.delete(storyDao.findAll().get(0));
		assertEquals(1,storyDao.findAll().size());
	}
	
	



}
