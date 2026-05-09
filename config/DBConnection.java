package config;
//waed rabah zaqout

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_course_system";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn;

    private DBConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException ex) {

            System.getLogger(DBConnection.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static DBConnection getInstance() {

        if (instance == null) {
            instance = new DBConnection();
        }

        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {

        if (conn == null || conn.isClosed()) {

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }

        return conn;
    }

    public synchronized void closeConnection() throws SQLException {

        if (conn != null) {

            if (!conn.isClosed()) {

                conn.close();
            }
        }
    }
}
