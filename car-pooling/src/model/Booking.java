package model;

import utils.IdGenerator;

import java.util.Date;

public class Booking {
    private final String bookingId;
    private final Person passenger;
    private final Ride ride;
    private final Date timestamp;
    public Booking(Person passenger, Ride ride) {
        this.bookingId = IdGenerator.nextId("B");
        this.passenger = passenger;
        this.ride      = ride;
        this.timestamp = new Date();
    }
    // getters...
    public String getBookingId() { return bookingId; }
    public Person getPassenger() { return passenger; }
    public Ride getRide()        { return ride; }
    public Date getTimestamp()   { return timestamp; }
}