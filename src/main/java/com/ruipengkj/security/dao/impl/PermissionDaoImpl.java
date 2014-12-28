package com.ruipengkj.security.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruipengkj.common.dao.BaseDaoImpl;
import com.ruipengkj.security.dao.PermissionDao;

@Repository
public class PermissionDaoImpl extends BaseDaoImpl implements PermissionDao {

	@Override
	public List<String> selectPermissionsByRoleName(String roleName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", roleName);
		String sql = "select ap.link from auth_permission ap, auth_role_permission arp, auth_role ar where ap.id = arp.permission_id and arp.role_id = ar.id and ar.name = :name";
		return this.selectList(sql, params, String.class);
	}

}
