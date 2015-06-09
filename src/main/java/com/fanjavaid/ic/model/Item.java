/**
 * 
 */
package com.fanjavaid.ic.model;

import java.util.Date;
import java.util.Set;

/**
 * @author fanjavaid
 *
 */
public class Item {
	private int id;
	
	private String name;
	
	private int qty;
	
	private Date expiredDate;
	
	private Set<Category> categories;
	
	private String returnMsg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", qty=" + qty
				+ ", expiredDate=" + expiredDate + ", categories=" + categories
				+ ", returnMsg=" + returnMsg + "]";
	}
}