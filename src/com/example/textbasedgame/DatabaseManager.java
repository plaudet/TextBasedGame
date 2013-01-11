package com.example.textbasedgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	private Connection conn;
	
	public DatabaseManager(){
		this.conn = null;
	}
	
	public void init() {
		String url = "jdbc:h2:/data/data/" +
			    "com.example.textbasedgame" +
			    "/data/hello" +
			    ";FILE_LOCK=FS" +
			    ";PAGE_SIZE=1024" +
			    ";CACHE_SIZE=8192";
			try {
				Class.forName("org.h2.Driver");
				Connection conn = DriverManager.getConnection(url);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	public Connection getConnection() {
		if ( this.conn != null ) {
			return this.conn;
		}
		return null;
	}
}
