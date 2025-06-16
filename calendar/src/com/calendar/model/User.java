package com.calendar.model;

import com.calendar.model.events.Event;
import com.calendar.model.intervals.TimeInterval;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// User class represents each user in the system
public class User {
    private String username;
    private String password;
    private TimeInterval workingHours;
    private List<Event> events;

    public User(String name, String password, TimeInterval workingHours) {
        this.username = name;
        this.password = password;
        this.workingHours = workingHours;
        this.events = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    public TimeInterval getWorkingHours() {
        return workingHours;
    }

    public boolean checkIfInWorkingHours(LocalTime timeToCheck){
        return workingHours.has(timeToCheck);
    }
}