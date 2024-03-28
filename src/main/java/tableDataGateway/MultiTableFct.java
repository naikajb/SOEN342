package tableDataGateway;

import java.sql.Connection;

public class MultiTableFct {
    private Connection conn;

    public String getAirportCodeForCity(String cityName) {
        CityDAO cityDAO = new CityDAO(conn);
        AirportDAO airportDAO = new AirportDAO(conn);

        long cityId = cityDAO.getCityIdByName(cityName);
        if (cityId == 0) {
            return null; // Or handle this as an error condition appropriately
        }

        return airportDAO.getAirportCodeByCityId(cityId);
    }
}
