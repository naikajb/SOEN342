package tableDataGateway;

import java.sql.Connection;

public class MultiTableFct {
    private Connection conn;

    public String getAirportCodeForCity(String cityName) {
        CityDAO cityDAO = new CityDAO(conn);
        AirportDAO airportDAO = new AirportDAO(conn);

        Long cityId = cityDAO.getCityIdByName(cityName);
        if (cityId == null) {
            return null; // Or handle this as an error condition appropriately
        }

        return airportDAO.getAirportCodeByCityId(cityId);
    }
}
