/**
 * 
 */
package com.fanjavaid.ic.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fanjavaid.ic.dao.ItemDao;
import com.fanjavaid.ic.model.Item;

/**
 * @author fanjavaid
 *
 */
public class ItemDaoImpl implements ItemDao {

	private Connection conn;
	
	public ItemDaoImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#insert(com.fanjavaid.ic.model.Item)
	 */
	@Override
	public void insert(Item i) throws SQLException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#update(com.fanjavaid.ic.model.Item)
	 */
	@Override
	public void update(Item i) throws SQLException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#delete(int)
	 */
	@Override
	public void delete(int itemId) throws SQLException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#selectAll()
	 */
	@Override
	public List<Item> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#selectById(int)
	 */
	@Override
	public Item selectById(int itemId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
