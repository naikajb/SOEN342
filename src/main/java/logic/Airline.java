package logic;

import java.util.ArrayList;

public class Airline {
    private String name;
    private ArrayList<Aircraft> aircraftList;

    public Airline(String n, ArrayList<Aircraft> list) {
        name = n;
        aircraftList = list;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public Aircraft checkAircraftAvailability() {
        if (aircraftList == null) {
            return null;
        } else {
            for (int i = 0; i < aircraftList.size(); i++) {
                if (aircraftList.get(i).checkAircraftAvailability()) {
                    return aircraftList.get(i);
                }
            }
            return null;
        }
    }

}
