package logic;

public class AirlineAdministrator extends Administrators {
    private String username;
    private String password;
    private long airline; // in DB: airlineId
    protected long id;
    protected String actorType;

    public AirlineAdministrator(String username, String password, long airline) {
        super();
        this.username = username;
        this.password = password;
        this.airline = airline;
    }

    public long getAirline(){
        return airline;
    }
}
