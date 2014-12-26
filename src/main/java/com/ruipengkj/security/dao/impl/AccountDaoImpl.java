package com.ruipengkj.security.dao.impl;

import java.util.List;

import com.ruipengkj.common.dao.BaseDaoImpl;
import com.ruipengkj.common.dao.Condition;
import com.ruipengkj.security.dao.AccountDao;
import com.ruipengkj.security.entity.Account;

public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

	@Override
	public Account selectByName(String accountName) {
		Condition<Account> condition = new Condition<Account>();
		condition.addExpression("account", Condition.EQ, accountName);
		//this.selectObject(entry, condition, Account.class);
		return null;
	}

	@Override
	public List<String> selectAccountRoleByName(String accountName) {
		// TODO Auto-generated method stub
		return null;
	}

}
