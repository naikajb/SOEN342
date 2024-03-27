package src.main.java.logic;

public abstract class Actors {
    protected boolean registered;
    protected String username;
    protected String password;

    public boolean getRegistered() {
        return registered;
    }

    protected Airport getAirportLocation() {
        return null;
    }
}
