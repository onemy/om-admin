package com.onemysoft.common.web;


/**
 * 分页专用的数据传输对象
 *
 */
public class DataTransferObject<T> {

	private long rows;
	private T datas;
	private int totalPageNums;
	private long totalRecordNums;
	private int draw;
	
	public T getDatas() {
		return datas;
	}
	public void setDatas(T datas) {
		this.datas = datas;
	}
	public void setTotalPageNums(int totalPageNums) {
		this.totalPageNums = totalPageNums;
	}
	public long getTotalRecordNums() {
		return totalRecordNums;
	}
	public void setTotalRecordNums(long totalRecordNums) {
		this.totalRecordNums = totalRecordNums;
	}
	
	public long getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public long getTotalPageNums() {
		try {
			return totalRecordNums % rows == 0 ? totalRecordNums/rows :  totalRecordNums/rows + 1;
		} catch (Exception e) {
			return totalPageNums;
		}
	}
	
	@Override
	public String toString() {
		return "DataTransferObject [rows=" + rows + ", datas=" + datas + ", totalPageNums="
				+ totalPageNums + ", totalRecordNums=" + totalRecordNums + "]";
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
}
