/**
 * 
 */
package com.fanjavaid.ic.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fanjavaid.ic.config.DbConn;
import com.fanjavaid.ic.dao.CategoryDao;
import com.fanjavaid.ic.dao.impl.CategoryDaoImpl;
import com.fanjavaid.ic.model.Category;

/**
 * @author fanjavaid
 *
 */
public class CategoryController {
	private CategoryDao dao;
	private Connection conn;
	
	public CategoryController() {
		conn = DbConn.getConn(); // Initializing connection here
		
		dao = new CategoryDaoImpl(conn);
	}
	
	public boolean create(String name) {
		boolean isSuccess = false;
		
		Category c = new Category();
		c.setName(name);
		
		try {
			dao.insert(c);
			
			isSuccess = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	public boolean update(String name, int id) {
		boolean isSuccess = false;
		
		Category c = new Category();
		c.setId(id);
		c.setName(name);
		
		try {
			dao.update(c);
			
			isSuccess = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	public boolean deleteCat(int id) {
		boolean isSuccess = false;
		
		try {
			dao.delete(id);
			
			isSuccess = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	public List<Category> fetchAll(int limit, int offset) {
		List<Category> list = null;
		Map<String, List<Category>> maps = new HashMap<String, List<Category>>();
		
		try {
			list = dao.selectAll(limit, offset);
			maps.put("data", list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Category fetchById(int id) {
		Category c = null;
		
		try {
			c = dao.selectById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	
	public long fetchCount() {
		long totalData = 0;
		
		try {
			totalData = dao.total();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return totalData;
	}
}
