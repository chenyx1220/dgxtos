package test.ruipengkj.security.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ruipengkj.security.dao.RoleDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-Context.xml" })
public class RoleDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	RoleDao roleDao;
	
	@Test
	public void testSelectRolesName() {
		List<String> list = roleDao.selectRolesName();
		Assert.assertTrue(list.size() > 0);
	}

}
