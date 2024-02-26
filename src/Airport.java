package src;

public class Airport {
    private String name;
    private String airportCode;
    private City location;

    public Airport(String n, String code, City loc){
        name = n;
        airportCode = code;
        location = loc;
    }

    public String getName(){
        return name;
    }

    public String getCode(){
        return airportCode;
    }

    public City getLocation(){
        return location;
    }


}
