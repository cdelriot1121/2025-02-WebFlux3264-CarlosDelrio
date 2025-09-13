package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ClassConection {

    static String URL = "jdbc:mysql://localhost:3306/estudianteTest";
    static String USERNAME = "root";
    static String PASSWORD = "rootcar";


    public static Connection getConnection() throws  SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
