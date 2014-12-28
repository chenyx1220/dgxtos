package test.ruipengkj.security.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ruipengkj.security.dao.AccountDao;
import com.ruipengkj.security.entity.Account;

/**
 * 引用org.junit.Assert;
 * assertEquals（期望值，实际值），检查两个值是否相等。
 * assertEquals（期望对象，实际对象），检查两个对象是否相等，利用对象的equals()方法进行判断。
 * assertSame（期望对象，实际对象），检查具有相同内存地址的两个对象是否相等，利用内存地址进行判断，注意和上面assertEquals方法的区别。
 * assertNotSame（期望对象，实际对象），检查两个对象是否不相等。
 * assertNull（对象1，对象2），检查一个对象是否为空。
 * assertNotNull（对象1，对象2），检查一个对象是否不为空。
 * assertTrue(布尔条件)，检查布尔条件是否为真。
 * assertFalse(布尔条件)，检查布尔条件是否为假。
 * @author Squall
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-Context.xml" })
public class AccountDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	AccountDao accountDao;

	@Test
	public void testSelectByName() {
		String accountName = "admin";
		Account account = accountDao.selectByName(accountName);
		Assert.assertNotNull(account);
	}
	
	@Test
	public void testSelectAccountRolesByName() {
		String accountName = "admin";
		List<String> list = accountDao.selectAccountRolesByName(accountName);
		Assert.assertTrue(list.size() > 0);
	}
}
