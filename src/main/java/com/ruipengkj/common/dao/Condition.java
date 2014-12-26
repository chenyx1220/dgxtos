package com.ruipengkj.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruipengkj.common.util.NameUtils;

public class Condition<T> {
	
	public static final String AND = " and ";//并且
	public static final String OR = " or ";//或者
	public static final String EQ = " = ";//等于
	public static final String NE = " != ";//不等于
	public static final String LT = " < ";//小于
	public static final String GT = " > ";//等于
	public static final String LE = " <= ";//小于等于
	public static final String GE = " >= ";//大于等于
	public static final String LIKE = " like ";//模糊匹配
	public static final Object BETWEEN = " between ";
	public static final String WHERE = " where ";//条件
	public static final String SET = " set ";//赋值
	public static final String ORDER_BY = " order by ";//排序
	public static final String ASC = "asc";//升序
	public static final String DESC = "desc";//降序
	
	
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

	public void selectField(String property) {
		fields.add(NameUtils.toUnderlineName(property));
	}
	public void addExpression(String property, String operator, Object value){
		String name = ":" + property;
		conditions.add(NameUtils.toUnderlineName(property) + operator + name);
		expressions.put(name, value);
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
