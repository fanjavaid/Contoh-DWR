/**
 * 
 */
package com.fanjavaid.ic.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fanjavaid.ic.config.DbConn;
import com.fanjavaid.ic.dao.ItemDao;
import com.fanjavaid.ic.dao.impl.ItemDaoImpl;
import com.fanjavaid.ic.model.Category;
import com.fanjavaid.ic.model.Item;

/**
 * @author fanjavaid
 *
 */
public class ItemController {
	private ItemDao dao;
	private Connection conn;
	private SimpleDateFormat sf;
	
	public ItemController() {
		conn = DbConn.getConn(); // Initializing connection here
		
		dao = new ItemDaoImpl(conn);
		sf = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public boolean create(String name, int qty, String expiredDate, int [] catId) {
		boolean isSuccess = false;
		
		Item i = new Item();
		i.setName(name);
		i.setQty(qty);
		
		Date expDate;
		try {
			expDate = sf.parse(expiredDate);
			i.setExpiredDate(expDate);
			
			Set<Category> categories = new HashSet<Category>();
			for (int categoryId : catId) {
				Category c = new Category();
				c.setId(categoryId);
				
				categories.add(c);
			}
			
			i.setCategories(categories);
			
			dao.insert(i);
			
			isSuccess = true;
		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	public boolean update(String name, int qty, String expiredDate, int [] catId, int itemId) {
		boolean isSuccess = false;
		
		Item i = new Item();
		i.setId(itemId);
		i.setName(name);
		i.setQty(qty);
		
		Date expDate;
		try {
			expDate = sf.parse(expiredDate);
			i.setExpiredDate(expDate);
			
			Set<Category> categories = new HashSet<Category>();
			for (int categoryId : catId) {
				Category c = new Category();
				c.setId(categoryId);
				
				categories.add(c);
			}
			
			i.setCategories(categories);
			
			dao.update(i);
			
			isSuccess = true;
		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	public boolean deleteItem(int id) {
		boolean isSuccess = false;
		
		try {
			dao.delete(id);
			
			isSuccess = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	public List<Item> fetchAll(int limit, int offset) {
		List<Item> items = null;
		
		try {
			items = dao.selectAll(limit, offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public Item fetchById(int id) {
		Item item = null;
		
		try {
			item = dao.selectById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return item;
	}
	
	public long fetchCount() {
		long total = 0;
		
		try {
			total = dao.total();
		} catch (SQLException e) {
			total = -1;
			e.printStackTrace();
		}
		
		return total;
	}
	
}
