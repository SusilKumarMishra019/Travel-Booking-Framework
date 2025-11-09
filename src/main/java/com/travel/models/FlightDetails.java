package com.travel.models;

public class FlightDetails {
    private String airline;
    private String departureTime;
    private int price;

    public FlightDetails(String airline, String departureTime, int price) {
        this.airline = airline;
        this.departureTime = departureTime;
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Airline: %s | Time: %s | Price: ₹%d", 
            airline, departureTime, price);
    }
}