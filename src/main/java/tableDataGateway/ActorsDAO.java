package tableDataGateway;

import logic.Actors;

import java.sql.Connection;

public class ActorsDAO {
    private Connection conn;

    public ActorsDAO(Connection conn) {
        this.conn = conn;
    }

    public void getUser(String username, String password){
        String sql = "select  * from Actors where Username = \' " + username + " AND Password = \'" + password + "\';";
    }
}
