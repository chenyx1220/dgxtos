package com.ruipengkj.common.bean.support;

public class Limitation {
	
	private Integer offset = new Integer(0); // 开始行
	private Integer rows = new Integer(10); // 记录数量
	
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	

}
