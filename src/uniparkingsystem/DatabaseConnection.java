package uniparkingsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	
	private static final String URL =
			"jdbc:mysql://localhost:3306/parking_database";
	
	private static final String USER = "root";

    private static final String PASSWORD = "cj122006";

    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
            );

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}


