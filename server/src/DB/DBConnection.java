package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;

    public Connection getConnect(String url, String login, String password){
        try {
            if (connection == null) {
                return DriverManager.getConnection(url, login, password);
            } else return connection;
        }catch (SQLException e ){
            System.out.println("Невозможно подключиться к Базе Данных");
            System.exit(1);
            return null;
        }
    }
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
