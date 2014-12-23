package com.ruipengkj.common.bean.support;

public class Pagination {

	/**
	 * 当前页
	 */
	private int currentPage = 1;

	/**
	 * 总共页数
	 */
	private int totalPages = 0;

	/**
	 * 每页记录数，默认10
	 */
	private int pageRecorders = 10;

	/**
	 * 总共记录数
	 */
	private int totalRows = 0;

	/**
	 * 每页开始记录数
	 */
	private int pageStartRow = 0;

	/**
	 * 每页结束记录数
	 */
	private int pageEndRow = 0;

	/**
	 * 是否存在下一页
	 */
	private boolean hasNextPage = false;

	/**
	 * 是否存在上一页
	 */
	private boolean hasPreviousPage = false;

	/**
	 * 下一页
	 */
	private int nextPage = 0;

	/**
	 * 上一页
	 */
	private int previousPage = 0;

	private String sorterName = "id";

	private String sorterDirection = "asc";

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageRecorders() {
		return pageRecorders;
	}

	public void setPageRecorders(int pageRecorders) {
		this.pageRecorders = pageRecorders;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		if (totalRows > 0) {
			// 计算总页数
			this.totalPages = (totalRows + this.pageRecorders - 1)
					/ this.pageRecorders;

			// 设定当前页
			/*
			 * if (this.currentPage > 1) { this.currentPage = this.currentPage -
			 * 1; if (this.currentPage * pageRecorders < totalRows) {
			 * this.currentPage = this.currentPage + 1; } }
			 */

			// 判断是否存在前一页
			if ((this.currentPage - 1) > 0) {
				this.hasPreviousPage = true;
				this.previousPage = this.currentPage - 1;
			} else {
				this.hasPreviousPage = false;
				this.previousPage = this.currentPage;
			}

			// 判断是否存在下一页
			if (this.currentPage >= this.totalPages) {
				this.hasNextPage = false;
				// 计算下一页
				this.nextPage = this.currentPage;
			} else {
				this.hasNextPage = true;
				// 计算下一页
				this.nextPage = this.currentPage + 1;
			}

			if (this.currentPage * this.pageRecorders < totalRows) {
				// 计算开始与结束记录数
				this.pageEndRow = this.currentPage * this.pageRecorders;
				this.pageStartRow = this.pageEndRow - this.pageRecorders;

			} else {
				// 计算结束记录数
				this.pageEndRow = totalRows;

				// 没有记录时，计算开始记录数
				if (this.totalPages == 0) {
					this.pageStartRow = 0;
				} else {
					// 有记录时，计算开始记录数
					this.pageStartRow = this.pageRecorders
							* (this.totalPages - 1);
				}
			}
		}
		this.totalRows = totalRows;
	}

	public int getPageStartRow() {
		return pageStartRow;
	}

	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	public int getPageEndRow() {
		return pageEndRow;
	}

	public void setPageEndRow(int pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}
	
	public String getSorterName() {
		return sorterName;
	}

	public void setSorterName(String sorterName) {
		this.sorterName = sorterName;
	}

	public String getSorterDirection() {
		return sorterDirection;
	}

	public void setSorterDirection(String sorterDirection) {
		this.sorterDirection = sorterDirection;
	}
	
	public Pagination(){}
	/**
	 * 构造分页帮助类
	 * 
	 * @param pageRecorders
	 *            每页显示记录数
	 * @param currentPage
	 *            当前页
	 */
	public Pagination(int currentPage, int pageRecorders) {
		this.currentPage = currentPage;
		this.pageRecorders = pageRecorders;
		this.pageStartRow = 0;
	}

}
