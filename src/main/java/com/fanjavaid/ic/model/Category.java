/**
 * 
 */
package com.fanjavaid.ic.model;

/**
 * @author fanjavaid
 *
 */
public class Category {
	private int id;
	
	private String name;
	
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

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", returnMsg="
				+ returnMsg + "]";
	}
	
	
}
