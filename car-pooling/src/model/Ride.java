package model;

import utils.IdGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class Ride {
    private final String rideId;
    private final Location start, end;
    private final Person driver;
    private final int maxSeats;
    private final AtomicInteger availableSeats;

    public Ride(Person driver, Location start, Location end, int maxSeats) {
        this.rideId = IdGenerator.nextId("R");;
        this.start = start;
        this.end = end;
        this.driver = driver;
        this.maxSeats = maxSeats;
        this.availableSeats = new AtomicInteger(maxSeats);
    }

    public String getRideId() {
        return rideId;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public Person getDriver() {
        return driver;
    }

    public int getAvailableSeats() {
        return availableSeats.get();
    }

    /**
     * Try to book one seat atomically. Returns true if successful.
     */
    public boolean bookSeat() {
        return availableSeats.getAndUpdate(current -> {
            if (current <= 0) return current; // no change
            return current - 1;
        }) > 0;
    }

    public void cancelSeat() {
        availableSeats.incrementAndGet();
    }
}
