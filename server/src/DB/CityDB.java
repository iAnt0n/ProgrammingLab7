package DB;

import collection.*;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

public class CityDB {
    private static Connection connection;
    private static String tablename;

    public CityDB(Connection connect, String tablename) {
        connection = connect;
        CityDB.tablename = tablename;
    }

    public static void insert(City city, String key) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into " + tablename + "(name,coordx,coordy,date,area,population,metersabovesealevel,climate,government,standartofliving,govname,govage,govhei,key) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        statement.setString(1, city.getName());
        statement.setInt(2, city.getCoordinates().getX());
        statement.setDouble(3, city.getCoordinates().getY());
        statement.setTimestamp(4,Timestamp.valueOf(city.getCreationDate()));
        statement.setFloat(5, city.getArea());
        statement.setLong(6, city.getPopulation());
        statement.setFloat(7, city.getMetersAboveSeaLevel());
        String climate ="";
        if (city.getClimate()!=null){
            climate = city.getClimate().toString();
        }
        statement.setString(8, climate);
        statement.setString(9, city.getGovernment().toString());
        String standart ="";
        if (city.getStandardOfLiving()!=null){
            standart = city.getStandardOfLiving().toString();
        }
        statement.setString(10, standart);
        statement.setString(11, city.getGovernor().getName());
        statement.setInt(12, city.getGovernor().getAge());
        statement.setDouble(13, city.getGovernor().getHeight());
        statement.setString(14, key);
        statement.execute();
        statement.close();
    }
    public static ConcurrentHashMap<String,City> loadMapFromDB() throws SQLException {
        ConcurrentHashMap<String,City> result = new ConcurrentHashMap<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from "+tablename);
        while(rs.next()){
            Climate climate=null;
            StandardOfLiving standard = null;
            if(!rs.getString(9).isEmpty()){
                climate=Climate.valueOf(rs.getString(9));
            }
            if(!rs.getString(11).isEmpty()){
                standard = StandardOfLiving.valueOf(rs.getString(11));
            }
            City city = new City(rs.getString("name"),
                    new Coordinates(rs.getInt(3),rs.getDouble(4)),
                    rs.getFloat(6), rs.getLong(7),
                    rs.getFloat(8), climate,
                    Government.valueOf(rs.getString(10)), standard,
                    new Human(rs.getString(12),rs.getInt(13),rs.getDouble(14)));
            city.setCreationDate(rs.getTimestamp(5).toLocalDateTime());
            city.setId(rs.getInt("id"));
            result.put(rs.getString("key"),city);
        }
        statement.close();
        return result;
    }
}
