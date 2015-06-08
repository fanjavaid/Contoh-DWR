/**
 * 
 */
package com.fanjavaid.ic.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author fanjavaid
 *
 */
public class DbConn {
	private static Connection conn;

	public static Connection getConn() {
		
		if (conn == null) {
			String driver = "com.mysql.jdbc.Driver";
			String url    = "jdbc:mysql://localhost:3306/db_itemcat";
			String user   = "root";
			String paswd  = "";
			
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, paswd);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			
		}
		
		return conn;
	}
}
