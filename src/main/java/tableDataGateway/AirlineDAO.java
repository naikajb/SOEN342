package tableDataGateway;

import java.sql.Connection;

public class AirlineDAO {
    private Connection conn;

    public AirlineDAO(Connection conn) {
        this.conn = conn;
    }
}
