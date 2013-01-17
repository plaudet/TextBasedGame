package com.example.textbasedgame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingsController {

	public BuildingsController() {
		ResultSet rs;
		Connection conn = new H2Controller().getConnection();
		PreparedStatement prepSelect;
		try {
			prepSelect = conn.prepareStatement("select * from tblBuildings");
			rs = prepSelect.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
