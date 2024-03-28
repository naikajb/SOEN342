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

    // public Aircraft checkAircraftAvailability() {
    // if (aircraftList == null) {
    // return null;
    // } else {
    // for (int i = 0; i < aircraftList.size(); i++) {
    // if (aircraftList.get(i).checkAircraftAvailability()) {
    // return aircraftList.get(i);
    // }
    // }
    // return null;
    // }
    // }

}
