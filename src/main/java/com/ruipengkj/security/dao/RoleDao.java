package com.ruipengkj.security.dao;

import java.util.List;

import com.ruipengkj.common.dao.BaseDao;

public interface RoleDao extends BaseDao {

	/**
	 * 搜索所有角色名
	 * @return
	 */
	List<String> selectAllRoleName();
}
