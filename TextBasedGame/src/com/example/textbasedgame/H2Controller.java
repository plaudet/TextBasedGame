package com.example.textbasedgame;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class H2Controller extends Activity {

	private StringBuilder log;
	private long start;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView sv = new ScrollView(this);
		TextView tv = new TextView(this);
		tv.setHorizontallyScrolling(true);
		tv.setVerticalScrollBarEnabled(true);
		String s = "";
		s += runTest();
		tv.setText(s);
		sv.addView(tv);
		setContentView(sv);
	}

	ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

	private String runTest() {
		System.gc();
		start();
		log("-- started h2 db");
		try {
			File db = new File("/data/data/com.example.textbasedgame/data/TextBasedGame.db");
			if (db.exists()) {
				log("deleted: " + db.delete());
			}
			Connection conn = null;
			String url = "jdbc:h2:/data/data/com.example.textbasedgame/data/hello;FILE_LOCK=FS";
			Class.forName("org.h2.Driver");
			log("classForName");
			conn = DriverManager.getConnection(url);
			Statement stat = conn.createStatement();
			try {
				stat.execute("create table if not exists test(id int primary key, name varchar)");
				log("created");
				ResultSet rs;
				PreparedStatement prepSelect = conn
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
	}

	private void start() {
		log = new StringBuilder();
		start = System.currentTimeMillis();
	}

	private void log(String s) {
		long t = System.currentTimeMillis() - start;
		log.append(t).append(':').append(s).append(" \n");
	}

}