package tableDataGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.Airport;
import logic.Flight;

public class FlightsDAO {
    private Connection conn;

    public FlightsDAO(Connection conn) {
        this.conn = conn;
    }
}
