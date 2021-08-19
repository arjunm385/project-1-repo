package com.spicybank.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {
	private static Connection connection;

	private PostgresConnection() {

	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=spicybank_db";
		String user = "postgres";
		String password = "123";
		connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

}

