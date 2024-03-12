package src.logic;

import java.util.ArrayList;

public class Airline {
    private String name;
    private ArrayList<Aircraft> aircraftList;

    public Airline(String n, ArrayList<Aircraft> list){
        name = n;
        aircraftList = list;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Aircraft> getAircraftList(){
        return aircraftList;
    }

    public boolean checkAircraftAvailability(){
        return false;
    }

}