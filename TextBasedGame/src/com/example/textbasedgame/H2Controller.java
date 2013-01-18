package com.example.textbasedgame;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class H2Controller {

	private String DB_NAME = "textbasegame.log";
	private String PATH = "/data/data/com.example.textbased.game/data/";
	private Connection conn = null;
	
	public H2Controller() {
		String url = "jdbc:h2:"+PATH+"/"+DB_NAME+";FILE_LOCK=FS";
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		createDb();
	}

	public Connection getConnection() {
		return conn;
	}
	
	private boolean isExist() {
		File db = new File(PATH + DB_NAME);
		return db.exists();
	}
	
	private void createDb(){
		if (isExist()) {
			return;
		}
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.execute("create table if not exists tblBuildings(id int primary key, name varchar, description varchar)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/*private String runTest() {
		try {
			
			
			try {
						.prepareStatement("select * from test");
				int count = 0;
				rs = prepSelect.executeQuery();
				while (rs.next()) {
					rs.getInt(1);
					rs.getString(2);
					count++;
				}
				log("select " + count);
				stat.execute("delete from test");
				conn.commit();
				log("deleted");
				conn.setAutoCommit(false);
				PreparedStatement prep = conn
						.prepareStatement("insert into test values(?, 'Hello')");
				for (int i = 0; i < 1000; i++) {
					prep.setInt(1, i);
					prep.execute();
				}
				conn.commit();
				log("inserted 1000");
			} finally {
				conn.close();
				log("closeConn");
			}
		} catch (Throwable e) {
			log(e.getMessage());
		}
		return log.toString();
	}*/
}