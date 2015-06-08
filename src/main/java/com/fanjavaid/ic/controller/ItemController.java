/**
 * 
 */
package com.fanjavaid.ic.controller;

import java.sql.Connection;

import com.fanjavaid.ic.config.DbConn;
import com.fanjavaid.ic.dao.ItemDao;
import com.fanjavaid.ic.dao.impl.ItemDaoImpl;

/**
 * @author fanjavaid
 *
 */
public class ItemController {
	private ItemDao dao;
	private Connection conn;
	
	public ItemController() {
		conn = DbConn.getConn(); // Initializing connection here
		
		dao = new ItemDaoImpl(conn);
	}
	
}
