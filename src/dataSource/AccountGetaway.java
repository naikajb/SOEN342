package src.dataSource;

import java.sql.*;

public class AccountGetaway {
    private Connection connection;

    public AccountGetaway(String databaseName) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        //will connect to database and create it if it's not found in the current directory
        String url = "jdbc:sqlite:" + databaseName;
        connection = DriverManager.getConnection(url);
        System.out.println("Successfully connected to the database");

    }

    public boolean authenticate(String username, String password) throws SQLException{

        return false;
    }
}
