package co.edu.icesi.fi.tics.tssc.daoTests;

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
import co.edu.icesi.fi.tics.tssc.dao.TsscAdminDao;
import co.edu.icesi.fi.tics.tssc.model.TsscAdmin;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TallerThymeleafApplication.class)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class TsscAdminDaoTest {
	
	@Autowired
	private TsscAdminDao adminDao;
	private TsscAdmin admin1;
	private TsscAdmin admin2;
	
	public void setUp()
	{
		admin1 = new TsscAdmin();
		admin1.setUser("Admin 1");
		admin2 = new TsscAdmin();
		admin2.setUser("Admin 2");
	}

	
	
	@Test
	@Order(1)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Add Admin")
	public void test1()
	{
		setUp();
		assertEquals(0,adminDao.findAll().size());
		adminDao.save(admin1);
		adminDao.save(admin2);
		assertEquals(2,adminDao.findAll().size());
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Update topic")
	public void test2()
	{
		TsscAdmin t = adminDao.findById(2);
		String user = "Usuario";
		t.setUser(user);
		adminDao.update(t);
		assertEquals(user, adminDao.findById(2).getUser());
	}
	
	@Test
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Find by id")
	public void test3()
	{
		List<TsscAdmin> stories = adminDao.findAll();
		assertEquals(stories.get(0), adminDao.findById(1));
	}
	

	
	@Test
	@Order(3)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DisplayName("Delete topic")
	public void test6()
	{
		assertEquals(2,adminDao.findAll().size());
		adminDao.delete(adminDao.findAll().get(0));
		assertEquals(1,adminDao.findAll().size());
	}
	
	


}
