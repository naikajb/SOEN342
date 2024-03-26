package logic;

public class Users extends Actors {
    public Users(boolean reg) {
        registered = reg;
    }

    public Users(String username, String password) {
        this(true);
        this.username = username;
        this.password = password;
    }

}
