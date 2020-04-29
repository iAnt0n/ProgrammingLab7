package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;

    public Connection getConnect(String url, String login, String password) throws SQLException {
        try {
            if (connection == null) {
                return DriverManager.getConnection(url, login, password);
            } else return connection;
        }catch (SQLException e){
            System.out.println(e.getMessage()+"\nНевозможно подключиться к Базе Данных");
            System.exit(1);
            return null;
        }
    }

}
