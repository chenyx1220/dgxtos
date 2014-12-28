package com.ruipengkj.security.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruipengkj.common.dao.BaseDaoImpl;
import com.ruipengkj.common.dao.Condition;
import com.ruipengkj.security.dao.AccountDao;
import com.ruipengkj.security.entity.Account;

@Repository
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

	@Override
	public Account selectByName(String accountName) {
		Condition condition = new Condition(Account.class);
		condition.addExpression("account", Condition.EQ, accountName);
		return this.selectObject(condition, Account.class);
	}

	@Override
	public List<String> selectAccountRolesByName(String accountName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", accountName);
		String sql = "select r.name from auth_role as r left join auth_account_role as ar on r.id = ar.role_id left join auth_account as a on ar.account_id = a.id where a.account = :account";
		return this.selectList(sql, params, String.class);
	}

}
