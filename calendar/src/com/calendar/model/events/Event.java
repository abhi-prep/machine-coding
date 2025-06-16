package com.calendar.model.events;

import com.calendar.model.intervals.DateTimeInterval;
import com.calendar.model.User;

import java.util.Set;

// Event class represents a calendar event
public class Event {

    String title;
    DateTimeInterval datetimeInterval;
    User organiser;
    Set<User> participants;

    public Event(String title, User owner, DateTimeInterval datetimeInterval, Set<User> participants ) {
        this.title = title;
        this.datetimeInterval = datetimeInterval;
        this.organiser = owner;
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public DateTimeInterval getDatetimeInterval() {
        return datetimeInterval;
    }

    public void setDatetimeInterval(DateTimeInterval datetimeInterval) {
        this.datetimeInterval = datetimeInterval;
    }

    public void addParticipant(User user) {
        participants.add(user);
    }

    public void removeParticipant(User user) {
        participants.remove(user);
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public User getOrganiser() {
        return organiser;
    }

    public void setOrganiser(User organiser) {
        this.organiser = organiser;
    }
}