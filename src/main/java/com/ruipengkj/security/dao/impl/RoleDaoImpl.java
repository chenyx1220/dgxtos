package com.ruipengkj.security.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruipengkj.common.dao.BaseDaoImpl;
import com.ruipengkj.common.dao.Condition;
import com.ruipengkj.security.dao.RoleDao;
import com.ruipengkj.security.entity.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	public List<String> selectRolesName() {
//		String sql = "select name from auth_role";
//		return this.selectList(sql, String.class);
		Condition condition = new Condition(Role.class);
		condition.addField("name");
		return this.selectList(condition, String.class);
    }
	
}
