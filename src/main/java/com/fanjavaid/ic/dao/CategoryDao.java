/**
 * 
 */
package com.fanjavaid.ic.dao;

import java.sql.SQLException;
import java.util.List;

import com.fanjavaid.ic.model.Category;

/**
 * @author fanjavaid
 *
 */
public interface CategoryDao {
	void insert(Category c) throws SQLException;
	
	void update(Category c) throws SQLException;
	
	void delete(int cId) throws SQLException;
	
	List<Category> selectAll(int limit, int offset) throws SQLException;
	
	Category selectById(int cId) throws SQLException;
	
	long total() throws SQLException;
}
