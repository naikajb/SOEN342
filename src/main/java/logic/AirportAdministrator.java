package src.main.java.logic;

public class AirportAdministrator extends Administrators {
    private final Airport location;

    public AirportAdministrator(Airport loc, String username, String password) {
        super();
        this.username = username;
        this.password = password;
        location = loc;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Airport getAirportLocation() {
        return location;
    }
}
