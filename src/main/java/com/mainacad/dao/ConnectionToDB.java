package com.mainacad.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

class ConnectionToDB {

	private static final Logger LOG = Logger.getLogger(ConnectionToDB.class.getName());
	private static final String URL = "jdbc:postgresql://localhost:5432/e_shop";
	private static final String USER = "postgres";
	private static final String PASSWORD = "215512";

	static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			LOG.severe("Connection to db denied!");
		}
		return connection;
	}
}
