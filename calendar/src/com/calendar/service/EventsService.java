package com.calendar.service;

import com.calendar.model.User;
import com.calendar.model.events.Event;
import com.calendar.model.events.RecurringEvent;
import com.calendar.model.intervals.DateTimeInterval;

import java.time.LocalDateTime;
import java.util.*;

public class EventsService {
    private final Map<String, Event> events;

    public EventsService() {
        this.events = new HashMap<>();
    }

    public Event createEvent(User user, String title, LocalDateTime startTime, LocalDateTime endTime, Set<User> participants) {
        Event event = new Event(title, user, new DateTimeInterval(startTime, endTime), participants);
        // Check if the event lies in the working hours of all the participants
        for (User participant: event.getParticipants()) {
            if(!(participant.checkIfInWorkingHours(startTime.toLocalTime()) && participant.checkIfInWorkingHours(endTime.toLocalTime()))){
                System.out.println("Event: " + event.getTitle() + " is not in working hours for participant: " + participant.getUsername());
                return null;
            }
        }

        // Add event to all the participants
        for (User participant: event.getParticipants()) {
            participant.addEvent(event);
        }

        events.put(event.getTitle(), event);
        System.out.println("Event created: " + title);
        return event;
    }

    // Recurring Events
    public void createRecurringEvent(User user, String title, LocalDateTime startTime, LocalDateTime endTime, Set<User> participants, int recurringCount) {
        for (int i = 0; i < recurringCount; i++) {
            Event event = new RecurringEvent(title, user, new DateTimeInterval(startTime.plusDays(i), endTime.plusDays(i)), participants, recurringCount);
            for (User participant: event.getParticipants()) {
                if(participant.checkIfInWorkingHours(startTime.toLocalTime()) && participant.checkIfInWorkingHours(endTime.toLocalTime())){
                    participant.addEvent(event);
                } else {
                    System.out.println("Event: " + event.getTitle() + " is not in working hours for participant: " + participant.getUsername());
                    return;
                }
            }
            events.put(event.getTitle(), event);
            System.out.println("Recurring Event created: " + title);
        }
    }

    public void deleteEvent(User user, Event event) {
        if(event.getOrganiser() == user){
            for (User participant: event.getParticipants()) {
                participant.deleteEvent(event);
            }
            events.remove(event.getTitle());
            System.out.println("Event deleted: " + event.getTitle());
        }
        else {
            System.out.println(user.getUsername() + " is not authorised to delete " + event.getTitle());
        }
    }

    public List<Event> fetchEventsByUser(User user){
        List<Event> userEvents = new ArrayList<>();
        for (Event event : events.values()) {
            if (event.getParticipants().contains(user)) {
                userEvents.add(event);
            }
        }
        return userEvents;
    }

}
