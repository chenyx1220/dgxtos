package com.ruipengkj.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Condition {
	private List<String> fields = null;
	private List<String> conditions = null;
	private Map<String, Object> expressions = null;
	private String orderBy = null;
	private int offset = 0;
	private int rows = 10;
	public Condition() {
		fields = new ArrayList<String>();
		conditions = new ArrayList<String>();
		expressions = new HashMap<String, Object>();
	}
	public void selectField(String field) {
		fields.add(field);
	}
	public void addExpression(String expression){
		conditions.add(expression);
	}
	public void addParam(String key, Object value){
		expressions.put(key, value);
	}
	public void orderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public void limit(int offset, int rows) {
		this.offset = offset;
		this.rows = rows;
	}
	public List<String> getFields() {
		return fields;
	}
	public List<String> getConditions() {
		return conditions;
	}
	public Map<String, Object> getExpressions() {
		return expressions;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public int getOffset() {
		return offset;
	}
	public int getRows() {
		return rows;
	}
	
	

}
