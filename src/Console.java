package src;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {

    private static ArrayList<Flight> flightList = new ArrayList<Flight>();
    private static ArrayList<Airport> airportList = new ArrayList<Airport>();

    public static String viewBasicInfo(Airport source, Airport destination){
        ArrayList<NonPrivateFlight> temp = new ArrayList<NonPrivateFlight>();
        String info = "";
        for(int i=0;i < flightList.size();i++){
            if(flightList.get(i).getSource() == source && flightList.get(i).getDestination() == destination && flightList.get(i) instanceof NonPrivateFlight ){
                temp.add((NonPrivateFlight) flightList.get(i));
            }
        }
        if(temp.isEmpty()){
            info+= "ERROR: There is no flight that matches the Source/Destination Airport in the flight catalog or there is no Non-Private flights in the catalog.";
        }else{
            for(int i=0;i<temp.size();i++){
                info += "Flight: " + temp.get(i).getFlightNumber() + "\n";
                info += "Source: " + temp.get(i).getSource().getName() + "\n";
                info += "Destination: " + temp.get(i).getDestination().getName() + "\n";
                info += "Scheduled Departure: " + temp.get(i).getScheduledDeparture() + "\n";
                info += "Scheduled Arrival: " + temp.get(i).getScheduledArrival() + "\n";
                info += "Actual Departure: " + temp.get(i).getActualDeparture() + "\n";
                info += "Estimated Arrival: " + temp.get(i).getEstimatedArrival() + "\n";
            }

        }

        return info;
    }

    public static String viewFullInfo(Airport source, Airport destination){
        ArrayList<NonPrivateFlight> temp = new ArrayList<NonPrivateFlight>();
        String info = "";
        for(int i=0;i < flightList.size();i++){
            if(flightList.get(i).getSource() == source && flightList.get(i).getDestination() == destination && flightList.get(i) instanceof NonPrivateFlight ){
                temp.add((NonPrivateFlight) flightList.get(i));
            }
        }
        if(temp.isEmpty()){
            info+= "ERROR: There is no flight that matches the Source/Destination Airport in the flight catalog or there is no Non-Private flights in the catalog.";
        }else{
            for(int i=0;i<temp.size();i++){
                info += "Flight: " + temp.get(i).getFlightNumber() + "\n";
                info += "Source: " + temp.get(i).getSource().getName() + "\n";
                info += "Destination: " + temp.get(i).getDestination().getName() + "\n";
                info += "Aircraft ID: " + temp.get(i).getAircraft().getAircraftID() + "\n";
                info += "Scheduled Arrival: " + temp.get(i).getScheduledArrival() + "\n";
                info += "Actual Departure: " + temp.get(i).getActualDeparture() + "\n";
                info += "Estimated Arrival: " + temp.get(i).getEstimatedArrival() + "\n";
                try{
                    info += "Airline Name: " + temp.get(i).getAircraft().getAirline().getName() + "\n";
                }catch(NullPointerException e){
                    info += "There is no Airline associated to the Aircraft of the Flight";
                }
            }

        }

        return info;
    }

    public static String viewPrivateInfo(Airport source, Airport destination){
        ArrayList<NonPrivateFlight> temp = new ArrayList<NonPrivateFlight>();
        String info = "";
        for(int i=0;i < flightList.size();i++){
            if(flightList.get(i).getSource() == source && flightList.get(i).getDestination() == destination && flightList.get(i) instanceof NonPrivateFlight ){
                temp.add((NonPrivateFlight) flightList.get(i));
            }
        }
        if(temp.isEmpty()){
            info+= "ERROR: There is no flight that matches the Source/Destination Airport in the flight catalog";
        }else{
            for(int i=0;i<temp.size();i++){
                info += "Flight: " + temp.get(i).getFlightNumber() + "\n";
                info += "Source: " + temp.get(i).getSource().getName() + "\n";
                info += "Destination: " + temp.get(i).getDestination().getName() + "\n";
                info += "Aircraft ID: " + temp.get(i).getAircraft().getAircraftID() + "\n";
                info += "Scheduled Arrival: " + temp.get(i).getScheduledArrival() + "\n";
                info += "Actual Departure: " + temp.get(i).getActualDeparture() + "\n";
                info += "Estimated Arrival: " + temp.get(i).getEstimatedArrival() + "\n";
                try{
                    info += "Airline Name: " + temp.get(i).getAircraft().getAirline().getName() + "\n";
                }catch(NullPointerException e){
                    info += "There is no Airline associated to the Aircraft of the Flight";
                }
            }

        }

        return info;

    }


}
