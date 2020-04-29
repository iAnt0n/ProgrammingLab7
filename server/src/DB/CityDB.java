package DB;

import collection.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class CityDB {
    private static Connection connection;
    private static String tablename;

    public CityDB(Connection connect, String tablename) {
        connection = connect;
        CityDB.tablename = tablename;
    }

    public static void insert(City city, String key,boolean id, String user) throws SQLException {
        PreparedStatement statement;
        if (!id){statement = connection.prepareStatement("insert into " + tablename + " values(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");}
        else{statement = connection.prepareStatement("insert into " + tablename + " values("+city.getId()+",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");}
        statement.setString(1, city.getName());
        statement.setInt(2, city.getCoordinates().getX());
        statement.setDouble(3, city.getCoordinates().getY());
        statement.setTimestamp(4,Timestamp.valueOf(city.getCreationDate()));
        statement.setFloat(5, city.getArea());
        statement.setLong(6, city.getPopulation());
        statement.setFloat(7, city.getMetersAboveSeaLevel());
        statement.setString(8, (city.getClimate()!=null) ? city.getClimate().toString()  : "");
        statement.setString(9, city.getGovernment().toString());
        statement.setString(10, (city.getStandardOfLiving()!=null) ? city.getStandardOfLiving().toString() : "");
        statement.setString(11, city.getGovernor().getName());
        statement.setInt(12, city.getGovernor().getAge());
        statement.setDouble(13, city.getGovernor().getHeight());
        statement.setString(14, key);
        statement.setString(15,user);
        statement.execute();
        statement.close();
    }
    public static ConcurrentHashMap<String,City> loadMapFromDB() throws SQLException {
        ConcurrentHashMap<String,City> result = new ConcurrentHashMap<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from "+tablename);
        while(rs.next()){
            Climate climate=(!rs.getString(9).isEmpty()) ? Climate.valueOf(rs.getString(9)) : null;
            StandardOfLiving standard = (!rs.getString(11).isEmpty()) ? StandardOfLiving.valueOf(rs.getString(11)) :null;
            City city = new City(rs.getString("name"),
                    new Coordinates(rs.getInt(3),rs.getDouble(4)),
                    rs.getFloat(6), rs.getLong(7), rs.getFloat(8), climate,
                    Government.valueOf(rs.getString(10)), standard,
                    new Human(rs.getString(12),rs.getInt(13),rs.getDouble(14)));
            city.setCreationDate(rs.getTimestamp(5).toLocalDateTime());
            city.setId(rs.getInt(1));
            result.put(rs.getString("key"),city);
        }
        statement.close();
        return result;
    }
    public static String clear(String user) throws SQLException {
        Statement stmnt = connection.createStatement();
        StringBuilder sb = new StringBuilder();
        stmnt.executeUpdate("delete from "+ tablename+" where \"user\" = '"+user+"'");
        ResultSet rs = stmnt.executeQuery("select * from "+tablename);
        while(rs.next()){
            sb.append(rs.getString("key")+",");
        }
        if(!sb.toString().isEmpty())sb.deleteCharAt(sb.length()-1);
        stmnt.close();
        return sb.toString();
    }
    public static void removeKey(String key,String user) throws SQLException {
        Statement stmnt = connection.createStatement();
        ResultSet rs = stmnt.executeQuery("select \"user\" from "+tablename+" where key ='"+key+"'");
        rs.next();
        if(!rs.getString("user").equals(user)) throw new SQLException("Извините, вы не имеете прав для модификации этого объекта");
        stmnt.executeUpdate("delete from "+tablename+ " where key = '"+key+"'");
        stmnt.close();
    }
    public static String removeLower(City city,String user) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder sb = new StringBuilder();
        statement.execute("delete from "+tablename+" where name < '"+city.getName()+"' and \"user\" = '"+user+"'");
        ResultSet rs = statement.executeQuery("select name from "+tablename+" where name < '"+city.getName()+"' and \"user\" != '"+user+"'");
        while(rs.next()){
            sb.append(rs.getString("name")+",");
        }
        if(!sb.toString().isEmpty())sb.deleteCharAt(sb.length()-1);
        statement.close();
        return sb.toString();
    }
    public static String removeLowerKey(String key,String user) throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder sb = new StringBuilder();
        statement.execute("delete  from "+tablename+" where key < '"+key+"' and \"user\" = '"+user+"'");
        ResultSet rs = statement.executeQuery("select key from "+tablename+" where key < '"+key+"' and \"user\" != '"+user+"'");
        while(rs.next()){
            sb.append(rs.getString("key")+",");
        }
        if(!sb.toString().isEmpty())sb.deleteCharAt(sb.length()-1);
        statement.close();
        return sb.toString();
    }
    public static void replaceIfLower(City city, String key ) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from "+tablename+" where name > '"+city.getName()+"' and key = '"+key+"'");
        if(rs.next()){
            if(!rs.getString("user").equals(city.getUser())) throw new SQLException("Извините вы не имеете прав модификации на объект City с ключом "+key);
            removeKey(key,city.getUser());
            city.setId(rs.getInt("id"));
            insert(city,key,true,city.getUser());
        }
        statement.close();
    }
    public static void updateID(City city, Integer id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from "+tablename+" where id = "+id);
        if(rs.next()){
            if(!rs.getString("user").equals(city.getUser())) throw new SQLException("Извините вы не имеете прав модификации на объект City с ID "+id);
            removeKey(rs.getString("key"),city.getUser());
            insert(city,rs.getString("key"),true,city.getUser());
        }
        statement.close();
    }
    public static Integer getLastID() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select lastval() from cities_id_seq");
        rs.next();
        return rs.getInt(1);
    }
}
