package com.ruipengkj.common.dao;

import java.util.List;

/**
 * sql的上下文内容
 * @author Squall
 *
 */
public class SqlContext {
    private StringBuilder sql;	//sql
    private List<Object>  params;	//参数

    public SqlContext(StringBuilder sql, List<Object> params) {
        this.sql = sql;
        this.params = params;
    }

	public StringBuilder getSql() {
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
    
    
}
