package com.iot.development;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IOTDevelopment {

	static {
		try {
			System.loadLibrary("mraajava");
		} catch (UnsatisfiedLinkError e) {
			System.err
					.println("Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n"
							+ e);
			System.exit(1);
		}
	}

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Some exception while looking for the driver.");
			e.printStackTrace();
			return;
		}

		Connection connection = null;

		try {
			// please correct the url path according to your created sql.
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:8080/ashish", "root", "password");

		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return;
		} finally {
			if (connection != null) {
				System.out
						.println("You made it, take control your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
		}
	}
}
