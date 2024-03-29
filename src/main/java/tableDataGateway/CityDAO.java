package tableDataGateway;

import java.sql.*;

public class CityDAO {
    private Connection conn;

    public CityDAO(Connection conn) {
        this.conn = conn;
    }


    public long getCityID(String cityName){
        long id = -1;
        String sql = "select  * from City where name = \'" +cityName + "\';";

        Statement s = null;
        ResultSet result;

        try{
            s = conn.createStatement();
            result = s.executeQuery(sql);

            if(result != null){
                id = result.getLong("id");
                //System.out.println("City " + cityName + " found in database with id " + id);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return id;
    }

    // Get CityID by given City Name
    public long getCityIdByName(String cityName) {
        String sql = "SELECT id FROM City WHERE name = \'" + cityName + "\';";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cityName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // City not found or error occurred
    }

    public long registerCity(String city, String country, Double temp) {

        long id = -1;
        String sql = null;

        PreparedStatement statement = null;
        ResultSet result = null;
        try{
            sql = "INSERT INTO City (name, Country, Temperature) VALUES (?, ?, ?)";
            statement = conn.prepareStatement(sql);

            // Set parameters
            statement.setString(1, city);
            statement.setString(2, country);
            statement.setDouble(3, temp);

            statement.executeUpdate();

            result = statement.getGeneratedKeys();
            if (result.next()) {
                id = result.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return id;
    }
}
