package test.ruipengkj.security.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ruipengkj.security.dao.PermissionDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-Context.xml" })
public class PermissionDaoTest {

	@Autowired
	PermissionDao permissionDao;
	
	@Test
	public void testSelectPermissionsByRoleName() {
		String roleName = "ROLE_ADMIN";
		List<String> list = permissionDao.selectPermissionsByRoleName(roleName);
		Assert.assertTrue(list.size() > 0);
	}
	
}
