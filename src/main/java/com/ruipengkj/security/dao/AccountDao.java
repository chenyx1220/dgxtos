package com.ruipengkj.security.dao;

import java.util.List;

import com.ruipengkj.common.dao.BaseDao;
import com.ruipengkj.security.entity.Account;

public interface AccountDao extends BaseDao {
	
	/**
	 * 根据帐号名查找帐号
	 * @param accountName
	 * @return
	 */
	Account selectByName(String accountName);
	
	/**
	 * 根据帐号名查找帐号角色
	 * @param accountName
	 * @return
	 */
	List<String> selectAccountRolesByName(String accountName);

}
