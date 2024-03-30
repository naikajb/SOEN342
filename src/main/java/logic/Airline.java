package logic;

public class Airline {
    private long id;
    private String name;

    public Airline(String n, long id) {
        this.name = n;
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
