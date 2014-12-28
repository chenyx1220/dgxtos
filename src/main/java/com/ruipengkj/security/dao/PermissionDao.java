package com.ruipengkj.security.dao;

import java.util.List;

import com.ruipengkj.common.dao.BaseDao;

public interface PermissionDao extends BaseDao {
	
	/**
	 * 根据角色名搜索所有权限
	 * @param roleName
	 * @return
	 */
	List<String> selectPermissionsByRoleName(String roleName);

}
