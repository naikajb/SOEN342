package tableDataGateway;

import java.sql.Connection;

import logic.Airport;

public class MultiTableFct {
    private Connection conn;

    public Airport getAirportCodeForCity(String cityName) {
        CityDAO cityDAO = new CityDAO(conn);
        AirportDAO airportDAO = new AirportDAO(conn);

        long cityId = cityDAO.getCityIdByName(cityName);
        if (cityId == 0) {
            return null; // Or handle this as an error condition appropriately
        }

        return airportDAO.getAirportByCityId(cityId);
    }
}
