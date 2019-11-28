package com.tavisca.javatraining.jee.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/jtraining";
	private static final String user = "root";
	private static final String password = "root";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(DB_URL, user, password);
	}
}
