package src.main.java.logic;

public class AirlineAdministrator extends Administrators {
    private String username;
    private String password;
    private Airline airline;

    public AirlineAdministrator(String username, String password, Airline airline) {
        super();
        this.username = username;
        this.password = password;
        this.airline = airline;
    }
}
