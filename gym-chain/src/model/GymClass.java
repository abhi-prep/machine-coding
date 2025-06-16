package model;

import java.time.LocalTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GymClass {
    private final int id;
    private final String type;
    private final int maxLimit;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Set<Integer> bookings = ConcurrentHashMap.newKeySet();

    public GymClass(int id, String type, int maxLimit, LocalTime start, LocalTime end) {
        this.id = id;
        this.type = type;
        this.maxLimit = maxLimit;
        this.startTime = start;
        this.endTime = end;
    }

    public int getId() {
        return id;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public boolean overlapsWith(LocalTime s, LocalTime e) {
        return !startTime.isAfter(e) && !endTime.isBefore(s);
    }

    public Set<Integer> getBookings() {
        return bookings;
    }

    public void addBooking(int bookingId) {
        bookings.add(bookingId);
    }

    public void removeBooking(int bookingId) {
        bookings.remove(bookingId);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
