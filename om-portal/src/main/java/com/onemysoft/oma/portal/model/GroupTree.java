package com.onemysoft.oma.portal.model;

public class GroupTree extends TreeModel<GroupTree> {

	private Integer orderNo;

	private String status;
	
	private String createDate;

	
	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
}
