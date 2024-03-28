package logic;

public class Administrators extends Actors {
    protected long id;
    protected boolean registered;
    protected String username;
    protected String password;
    protected long location; // in DB: airportId - for Airport Administrators
    protected long airline; // in DB: airlineId - for Airline Administrators
    protected String actorType;

    public Administrators() {
        registered = true;
    }
}