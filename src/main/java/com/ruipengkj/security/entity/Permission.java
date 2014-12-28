package com.ruipengkj.security.entity;

import com.ruipengkj.common.dao.Table;

@Table(tableName="auth_permission")
public class Permission {

	private Integer id;
	private Integer pid;
	private String name;
	private String link;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
