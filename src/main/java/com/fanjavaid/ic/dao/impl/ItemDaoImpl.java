/**
 * 
 */
package com.fanjavaid.ic.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fanjavaid.ic.dao.ItemDao;
import com.fanjavaid.ic.model.Category;
import com.fanjavaid.ic.model.Item;

/**
 * @author fanjavaid
 *
 */
public class ItemDaoImpl implements ItemDao {

	private Connection conn;
	
	private final String SQL_INSERT = "INSERT INTO tb_item VALUES (null, ?, ?, ?)";
	private final String SQL_INSERT_DETAIL = "INSERT INTO tb_item_category VALUES (null, ?, ?)";
	
	private final String SQL_UPDATE = "UPDATE tb_item SET name=?, qty=?, expired_date=? WHERE id=?";
	private final String SQL_UPDATE_DELETE = "DELETE FROM tb_item_category WHERE id_item = ?";
	
	private final String SQL_DELETE = "DELETE FROM tb_item WHERE id=?";
	private final String SQL_DELETE_DETAIL = "DELETE FROM tb_item_category WHERE id_item = ?";
	
	private final String SQL_SELECT = "SELECT a.*, GROUP_CONCAT(c.id,'-',c.name) as categories FROM tb_item a LEFT JOIN tb_item_category b ON a.id = b.id_item LEFT JOIN tb_category c ON c.id = b.id_category GROUP BY a.name ORDER BY a.name ASC LIMIT ?,?";
	private final String SQL_BY_ID  = "SELECT a.*, GROUP_CONCAT(c.id,'-',c.name) as categories FROM tb_item a LEFT JOIN tb_item_category b ON a.id = b.id_item LEFT JOIN tb_category c ON c.id = b.id_category WHERE a.id = ? GROUP BY a.name";
	private final String SQL_COUNT  = "SELECT COUNT(*) as total FROM tb_item";

	
	public ItemDaoImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#insert(com.fanjavaid.ic.model.Item)
	 */
	@Override
	public void insert(Item i) throws SQLException {
		PreparedStatement ps = null;
		PreparedStatement psDetail = null;
		
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, i.getName());
			ps.setInt(2, i.getQty());
			ps.setDate(3, new Date(i.getExpiredDate().getTime()));
			
			System.out.println("Insert Master : ");
			System.out.println(ps.toString());
			
			if (ps.executeUpdate() != -1) {
				rs = ps.getGeneratedKeys();
				
				// Dapatkan ID dari data yang diinsert
				int savedItemID = -1;
				if (rs.next()) {
					savedItemID = rs.getInt(1);
				}
				
				Set<Category> categories = i.getCategories();
				for (Category category : categories) {
					psDetail = conn.prepareStatement(SQL_INSERT_DETAIL);
					psDetail.setInt(1, savedItemID);
					psDetail.setInt(2, category.getId());
					psDetail.executeUpdate();
					
					System.out.println("Insert Detail : ");
					System.out.println(psDetail.toString());
				}
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#update(com.fanjavaid.ic.model.Item)
	 */
	@Override
	public void update(Item i) throws SQLException {
		PreparedStatement ps = null;
		PreparedStatement psDetail = null;
		PreparedStatement psDelete = null;
		
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(SQL_UPDATE);
			ps.setString(1, i.getName());
			ps.setInt(2, i.getQty());
			ps.setDate(3, new Date(i.getExpiredDate().getTime()));
			ps.setInt(4, i.getId());
			
			System.out.println("Update Master : ");
			System.out.println(ps.toString());
			
			if (ps.executeUpdate() != -1) {
				
				psDelete = conn.prepareStatement(SQL_UPDATE_DELETE);
				psDelete.setInt(1, i.getId());
				
				if (psDelete.executeUpdate() != -1) {
					Set<Category> categories = i.getCategories();
					for (Category category : categories) {
						psDetail = conn.prepareStatement(SQL_INSERT_DETAIL);
						psDetail.setInt(1, i.getId());
						psDetail.setInt(2, category.getId());
						psDetail.executeUpdate();
						
						System.out.println("Update with Insert Detail : ");
						System.out.println(psDetail.toString());
					}
				}
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#delete(int)
	 */
	@Override
	public void delete(int itemId) throws SQLException {
		PreparedStatement ps = null;
		PreparedStatement psDelDetail = null;
		
		try {
			psDelDetail  = conn.prepareStatement(SQL_DELETE_DETAIL);
			psDelDetail.setInt(1, itemId);
			
			System.out.println("Delete Detail : ");
			System.out.println(psDelDetail.toString());
			
			if (psDelDetail.executeUpdate() != -1) {
				ps = conn.prepareStatement(SQL_DELETE);
				ps.setInt(1, itemId);
				ps.executeUpdate();
				
				System.out.println("Delete Master : ");
				System.out.println(ps.toString());
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#selectAll()
	 */
	@Override
	public List<Item> selectAll(int limit, int offset) throws SQLException {
		List<Item> items = new ArrayList<Item>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(SQL_SELECT);
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			
			rs = ps.executeQuery();
			
			String [] cats = null;
			while (rs.next()) {
				Item i = new Item();
				Set<Category> categories = new HashSet<Category>();
				
				i.setId(rs.getInt("id"));
				i.setName(rs.getString("name"));
				i.setQty(rs.getInt("qty"));
				i.setExpiredDate(rs.getDate("expired_date"));
				
				cats = rs.getString("categories").split(",");
				for (String cat : cats) {
					String [] singleValue = cat.split("-");
					
					Category c = new Category();
					c.setId(Integer.parseInt(singleValue[0]));
					c.setName(singleValue[1]);
					c.setReturnMsg("success");
					
					System.out.println(c.toString());
					categories.add(c);
				}
				
				System.out.println();
				
				i.setCategories(categories);
				i.setReturnMsg("success");
				
				items.add(i);
			}
			
		} catch (SQLException se) {
			Item i = new Item();
			i.setReturnMsg("Error : " + se.getMessage());
			items.add(i);
			
			se.printStackTrace();
		}
		
		return items;
	}

	/* (non-Javadoc)
	 * @see com.fanjavaid.ic.dao.ItemDao#selectById(int)
	 */
	@Override
	public Item selectById(int itemId) throws SQLException {
		
		Item item = new Item();
		Set<Category> categories = new HashSet<Category>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(SQL_BY_ID);
			ps.setInt(1, itemId);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				item.setId(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setQty(rs.getInt("qty"));
				item.setExpiredDate(rs.getDate("expired_date"));
				
				String [] cats = rs.getString("categories").split(",");
				for (String cat : cats) {
					String [] singleValue = cat.split("-");
					
					for (String string : singleValue) {
						System.out.println(string);
					}
					
					
					Category c = new Category();
					c.setId(Integer.parseInt(singleValue[0]));
					c.setName(singleValue[1]);
					c.setReturnMsg("success");
					
					categories.add(c);
					
				}
				
				item.setCategories(categories);
				item.setReturnMsg("success");
			}
			
		} catch (SQLException se) {
			item.setReturnMsg("Error : " + se.getMessage());
			se.printStackTrace();
		}
		
		return item;
	}

	@Override
	public long total() throws SQLException {
		long total = 0;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL_COUNT);
			
			if (rs.next()) {
				total = rs.getLong("total");
			}
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		
		return total;
	}

}
