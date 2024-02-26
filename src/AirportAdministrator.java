package src;

public class AirportAdministrator extends Administrators{
    private Airport location;

    public AirportAdministrator(Airport loc){
        location = loc;
    }

    public Airport getAirportLocation(){
        return location;
    }
}
