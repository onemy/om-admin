package com.onemysoft.common.web;

/**
 * 页面请求的分页参数
 * @author onemysoft
 * 
 */
public class RequestTransferData {

	private int page;
	private int start;
	private int rows;
	private int draw;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getStart() {
		
		int _start = (page-1) * rows;
		
		if(!(_start < 0))
			return _start;
		else
			return start;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
}
