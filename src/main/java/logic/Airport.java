package logic;

public class Airport {

    private long id;
    private String name;
    private String airportCode; // like YUL
    private long location; // in DB: cityId

    public Airport(String n, String code, long location, long id) {
        name = n;
        airportCode = code;
        this.location = location;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return airportCode;
    }

    public long getLocation() {
        return location;
    }
}
