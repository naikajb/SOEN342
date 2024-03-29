package tableDataGateway;

import logic.Actors;
import logic.Airport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActorsDAO {
    private Connection conn;

    public ActorsDAO(Connection conn) {
        this.conn = conn;
    }

    public String[] getUserInfo(String username, String password){
        String[] userInfo = null;

        String sql = "select  * from Actors where Username = \'" +username + "\' AND Password = \'"+password+"\';";
        Statement statement = null;
        ResultSet result;
        try {
            statement = conn.createStatement();
             result = statement.executeQuery(sql);
             if(result == null){
                 System.out.println("The results are null");
                 return null;
             }else{
                 userInfo = new String[4];
                 userInfo[0] = result.getString("id");
                 userInfo[1] = result.getString("Discriminator");
                 userInfo[2] = result.getString("AirportID");
                 userInfo[3] = result.getString("AirlineID");
                 statement.close();
                 return userInfo;
             }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
