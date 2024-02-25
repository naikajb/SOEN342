package src;

import java.util.ArrayList;

public class Airline {
    private String name;
    private ArrayList<Aircraft> aircraftList;

    public Airline(String n, ArrayList<Aircraft> list){
        name = n;
        aircraftList = list;
    }

}
