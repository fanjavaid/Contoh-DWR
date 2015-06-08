/**
 * 
 */
package com.fanjavaid.ic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fanjavaid.ic.dao.CategoryDao;
import com.fanjavaid.ic.model.Category;

/**
 * @author fanjavaid
 *
 */
public class CategoryDaoImpl implements CategoryDao {
	
	private Connection conn;
	
	private final String SQL_INSERT = "INSERT INTO tb_category VALUES (null, ?)";
	private final String SQL_UPDATE = "UPDATE tb_category SET name=? WHERE id=?";
	private final String SQL_DELETE = "DELETE FROM tb_category WHERE id=?";
	private final String SQL_SELECT = "SELECT * FROM tb_category ORDER BY name ASC LIMIT ?,?";
	private final String SQL_BY_ID  = "SELECT * FROM tb_category WHERE id=?";
	private final String SQL_COUNT  = "SELECT COUNT(*) as total FROM tb_category";

	public CategoryDaoImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.CategoryDao#insert(com.fanjavaid.ic.model.Category)
	 */
	@Override
	public void insert(Category c) throws SQLException {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(SQL_INSERT);
			ps.setString(1, c.getName());
			
			ps.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.CategoryDao#update(com.fanjavaid.ic.model.Category)
	 */
	@Override
	public void update(Category c) throws SQLException {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(SQL_UPDATE);
			ps.setString(1, c.getName());
			ps.setInt(2, c.getId());
			
			ps.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.CategoryDao#delete(int)
	 */
	@Override
	public void delete(int cId) throws SQLException {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(SQL_DELETE);
			ps.setInt(1, cId);
			
			ps.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.CategoryDao#selectAll()
	 */
	@Override
	public List<Category> selectAll(int limit, int offset) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Category> categories = new ArrayList<Category>();
		
		try {
			ps = conn.prepareStatement(SQL_SELECT);
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setReturnMsg("success");
				
				categories.add(c);
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		return categories;
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.CategoryDao#selectById(int)
	 */
	@Override
	public Category selectById(int cId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Category c = null;
		
		try {
			ps = conn.prepareStatement(SQL_BY_ID);
			ps.setInt(1, cId);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setReturnMsg("success");
			} else {
				c = new Category();
				c.setId(0);
				c.setName(null);
				c.setReturnMsg("error");
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		return c;
	}

	@Override
	public long total() throws SQLException {
		long totalData = 0;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL_COUNT);
			
			if (rs.next()) {
				totalData = rs.getLong("total");
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		return totalData;
	}

}
