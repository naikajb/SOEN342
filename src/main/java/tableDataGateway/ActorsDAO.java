package tableDataGateway;

import java.sql.Connection;

public class ActorsDAO {
    private Connection conn;

    public ActorsDAO(Connection conn) {
        this.conn = conn;
    }
}
