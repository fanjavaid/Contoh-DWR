/**
 * 
 */
package com.fanjavaid.ic.dao;

import java.sql.SQLException;
import java.util.List;

import com.fanjavaid.ic.model.Item;

/**
 * @author fanjavaid
 *
 */
public interface ItemDao {
	void insert(Item i) throws SQLException;
	
	void update(Item i) throws SQLException;
	
	void delete(int itemId) throws SQLException;
	
	List<Item> selectAll() throws SQLException;
	
	Item selectById(int itemId) throws SQLException;
}
