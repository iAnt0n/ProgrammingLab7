package DB;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class ClientDB {
    private static Connection connection;
    private static String tablename;

    public ClientDB(Connection connect, String tablename) {
        connection = connect;
        ClientDB.tablename = tablename;
    }

    public void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException ignored){ }
    }

    public static String register(String name, char[] password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select username from "+tablename);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            if (name.equals(rs.getString("username"))) return "Пользователь с таким именем уже зарегистрирован";
        }
        statement = connection.prepareStatement("insert into "+tablename+" values (?, ?, ?)");
        String salt = getSalt();
        String hash = hash(new String(password), salt);
        statement.setString(1, name);
        statement.setString(2, hash);
        statement.setString(3, salt);
        statement.execute();
        statement.close();
        return "Пользователь "+name+" успешно зарегистрирован";
    }

    public static String login(String name, char[] password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from "+tablename+" where username = ?");
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        if (rs.next()&&hash(new String(password), rs.getString("salt"))
                .equals(rs.getString("password"))) {
                    return "Вход произошел успешно";
                }
        return "Неверное имя пользователя или пароль";
    }

    private static String hash(String password, String salt){
        try{
            String pepper = "22&3CdsFgh2cL97#3";
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] data = (pepper + password + salt).getBytes(StandardCharsets.UTF_8);
            byte[] hashbytes = md.digest(data);
            return Base64.getEncoder().encodeToString(hashbytes);
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }

    private static String getSalt(){
        byte[] salt = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        return new String(salt, StandardCharsets.UTF_8);
    }
}
