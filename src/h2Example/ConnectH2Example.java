package src.h2Example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectH2Example {

	public static void main(String[] args) {
		String jdbcURL = "jdbc:h2:mem:testdb";
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL);
			
			System.out.println("Connected to H2 in-memory database.");
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

}
