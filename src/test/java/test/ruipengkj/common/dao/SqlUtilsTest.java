package test.ruipengkj.common.dao;

import org.junit.Assert;
import org.junit.Test;

import com.ruipengkj.common.dao.Condition;
import com.ruipengkj.common.dao.SqlUtils;
import com.ruipengkj.security.entity.Account;

public class SqlUtilsTest {

	@Test
	public void testBuildInsert() {
		Account entity = new Account();
		entity.setAccount("admin");
		entity.setPassword("123456");
		String sql = SqlUtils.buildInsert(entity);
		System.out.println("Insert:" + sql);
		Assert.assertEquals("insert into auth_account(account,password) values (:account,:password)", sql);
	}

	@Test
	public void testBuildUpdate() {
		Account entity = new Account();
		entity.setAccount("admin");
		entity.setPassword("123456");
		Condition condition = new Condition(Account.class);
		condition.setEntity(entity);
		condition.addExpression("account", Condition.EQ, "admin");
		String sql = SqlUtils.buildUpdate(condition);
		System.out.println("UpdateByConditon:" + sql);
		Assert.assertEquals("update auth_account set account = :account,password = :password where account = :account", sql);
	}

	@Test
	public void testBuildDelete() {
		Condition condition = new Condition(Account.class);
		condition.addExpression("account", Condition.EQ, "admin");
		String sql = SqlUtils.buildDelete(condition);
		System.out.println("DeleteByCondition:" + sql);
		Assert.assertEquals("delete from auth_account where account = :account", sql);
	}

	@Test
	public void testBuildSelect() {
		Condition condition = new Condition(Account.class);
		condition.addExpression("account", Condition.EQ, "admin");
		String sql = SqlUtils.buildSelect(condition);
		System.out.println("SelectByCondition:" + sql);
		Assert.assertEquals("select * from auth_account where account = :account", sql);
		
		condition.addField("account");
		condition.addField("password");
		sql = SqlUtils.buildSelect(condition);
		System.out.println("SelectFieldByCondition:" + sql);
		Assert.assertEquals("select account,password from auth_account where account = :account", sql);
		
	}

	@Test
	public void testBuildBatchInsert() {
		String sql = SqlUtils.buildBatchInsert(Account.class);
		System.out.println("BatchInsert:" + sql);
		Assert.assertEquals("insert into auth_account(account,create_time,enabled,id,locked,password) values (:account,:createTime,:enabled,:id,:locked,:password)", sql);
	}

	@Test
	public void testBuildBatchUpdate() {
		Condition condition = new Condition(Account.class);
		condition.addExpression("account", Condition.EQ, "admin");
		String sql = SqlUtils.buildBatchUpdate(condition);
		System.out.println("BatchUpdateByCondition:" + sql);
		Assert.assertEquals("update auth_account set account = :account,create_time = :createTime,enabled = :enabled,locked = :locked,password = :password where account = :account", sql);
	}

}
