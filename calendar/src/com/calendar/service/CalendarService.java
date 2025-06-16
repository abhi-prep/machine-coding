package com.calendar.service;

import com.calendar.model.events.Event;
import com.calendar.model.intervals.DateTimeInterval;
import com.calendar.model.intervals.TimeInterval;
import com.calendar.model.User;

import java.time.LocalDateTime;
import java.util.*;

// CalendarService class is the main controller for the calendar service
public class CalendarService {

    private UserService userService;
    private EventsService eventsService;

    public CalendarService() {
        userService = new UserService();
        eventsService = new EventsService();
    }

    public void registerUser(String username, String password, TimeInterval workingHours) {
       userService.registerUser(username, password, workingHours);
    }

    public User loginUser(String username, String password) {
       return userService.loginUser(username, password);
    }

    public Event createEvent(User user, String title, LocalDateTime startTime, LocalDateTime endTime, Set<User> participants) {
        return eventsService.createEvent(user, title, startTime, endTime, participants);
    }

    public void createRecurringEvent(User user, String title, LocalDateTime startTime, LocalDateTime endTime, Set<User> participants, int recurringCount) {
        eventsService.createRecurringEvent(user, title, startTime, endTime, participants, recurringCount);
    }

    public void deleteEvent(User user, Event event) {
        eventsService.deleteEvent(user, event);
    }

    public List<Event> fetchEventsByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return eventsService.fetchEventsByUser(user);
    }

    public Set<Event> fetchConflictingEvents(User user) {
        return userService.fetchConflictingEvents(user);
    }

    public DateTimeInterval findFavorableSlot(Set<User> users, int durationInMinutes) {
        return userService.findFavorableSlot(users, durationInMinutes);
    }

}
