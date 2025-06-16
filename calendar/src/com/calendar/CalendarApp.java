package com.calendar;

import com.calendar.model.events.Event;
import com.calendar.model.intervals.DateTimeInterval;
import com.calendar.model.intervals.TimeInterval;
import com.calendar.model.User;
import com.calendar.service.CalendarService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

// CalendarApp class acts as the CLI interface for the calendar service
class CalendarApp {
    public static void main(String[] args) {

        CalendarService calendarService = new CalendarService();

        // Example test cases
        // Working Shift of 9am to 6pm for all the users
        LocalTime workingShiftStartTime = LocalTime.of( 9, 0);
        LocalTime workingShiftEndTime = LocalTime.of( 18, 0);

        calendarService.registerUser("UserA", "usera",  new TimeInterval(workingShiftStartTime, workingShiftEndTime));
        calendarService.registerUser("UserB","userb",  new TimeInterval(workingShiftStartTime, workingShiftEndTime));
        calendarService.registerUser("UserC", "userc",  new TimeInterval(workingShiftStartTime, workingShiftEndTime));

        User userA = calendarService.loginUser("UserA", "usera");
        User userB = calendarService.loginUser("UserB", "userb");
        User userC = calendarService.loginUser("UserC", "userc");

        // User A events
        LocalDateTime startTime = LocalDateTime.of(2023, 6, 1, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 6, 1, 14, 0);
        calendarService.createEvent(userA, "Event A1", startTime, endTime, new HashSet<>(Arrays.asList(userA)));

        startTime = LocalDateTime.of(2023, 6, 1, 17, 0);
        endTime = LocalDateTime.of(2023, 6, 1, 18, 0);
        calendarService.createEvent(userA, "Event A2", startTime, endTime, new HashSet<>(Arrays.asList(userA)));

        startTime = LocalDateTime.of(2023, 6, 1, 9, 0);
        endTime = LocalDateTime.of(2023, 6, 1, 10, 0);
        calendarService.createEvent(userA, "Event A3", startTime, endTime, new HashSet<>(Arrays.asList(userA)));

        startTime = LocalDateTime.of(2023, 6, 1, 9, 0);
        endTime = LocalDateTime.of(2023, 6, 1, 11, 0);
        Event event4 = calendarService.createEvent(userA, "Event A4", startTime, endTime, new HashSet<>(Arrays.asList(userA)));

        // Deleting Events
        calendarService.deleteEvent(userA, event4);
        calendarService.deleteEvent(userB, event4);

        // User B events
        startTime = LocalDateTime.of(2023, 6, 1, 9, 0);
        endTime = LocalDateTime.of(2023, 6, 1, 12, 0);
        calendarService.createEvent(userA, "Event B1", startTime, endTime, new HashSet<>(Arrays.asList(userB)));

        startTime = LocalDateTime.of(2023, 6, 1, 16, 0);
        endTime = LocalDateTime.of(2023, 6, 1, 18, 0);
        calendarService.createEvent(userA, "Event B2", startTime, endTime, new HashSet<>(Arrays.asList(userB)));

        // User C events
        startTime = LocalDateTime.of(2023, 6, 1, 9, 0);
        endTime = LocalDateTime.of(2023, 6, 1, 13, 0);
        calendarService.createEvent(userA, "Event C1", startTime, endTime, new HashSet<>(Arrays.asList(userC)));

        startTime = LocalDateTime.of(2023, 6, 1, 17, 0);
        endTime = LocalDateTime.of(2023, 6, 1, 18, 0);
        calendarService.createEvent(userA, "Event C2", startTime, endTime, new HashSet<>(Arrays.asList(userC)));

        // Can fetch its events
        List<Event> userAEvents = calendarService.fetchEventsByUsername(userA.getUsername());
        System.out.println("UserA Events:");
        for (Event event : userAEvents) {
            System.out.println(event.getTitle());
        }

        // Can fetch other's events
        List<Event> userBEvents = calendarService.fetchEventsByUsername("UserB");
        System.out.println("UserB Events:");
        for (Event event : userBEvents) {
            System.out.println(event.getTitle());
        }

        List<Event> userCEvents = calendarService.fetchEventsByUsername("UserC");
        System.out.println("UserC Events:");
        for (Event event : userCEvents) {
            System.out.println(event.getTitle());
        }

        System.out.println("UserA Conflicting Events:");
        for (Event event : calendarService.fetchConflictingEvents(userA)) {
            System.out.println(event.getTitle());
        }

        // FavorableSlot. Doesn't consider working hours
        DateTimeInterval favorableInterval = calendarService.findFavorableSlot(new HashSet<>(Arrays.asList(userA, userB, userC)), 120);
        System.out.println("Favorable Slot can be between:");
        System.out.println(favorableInterval.getStart().toString());
        System.out.println(favorableInterval.getEnd().toString());
    }
}