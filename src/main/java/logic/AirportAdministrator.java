package logic;

public class AirportAdministrator extends Administrators {
    private final long location; // in DB: cityId
    protected long id;
    protected boolean registered;
    protected String username;
    protected String password;
    protected String actorType;

    public AirportAdministrator(Long loc, String username, String password) {
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

    public long getLocation() {
        return location;
    }
}
