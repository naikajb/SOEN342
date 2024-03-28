package logic;

public class City {
    long id;
    String name;
    String country;
    Double temperature;

    public City(String n, String c, Double temp) {
        name = n;
        country = c;
        temperature = temp;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Double getTemperature() {
        return temperature;
    }
}
