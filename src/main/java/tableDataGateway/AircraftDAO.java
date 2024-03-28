package tableDataGateway;

import java.sql.Connection;

public class AircraftDAO {
    private Connection conn;

    public AircraftDAO(Connection conn) {
        this.conn = conn;
    }

}
