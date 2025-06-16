package com.calendar.model.events;

import com.calendar.model.intervals.DateTimeInterval;
import com.calendar.model.User;

import java.util.Set;

public class RecurringEvent extends Event {

    int recurringCount;
    public RecurringEvent(String title, User owner, DateTimeInterval datetimeInterval, Set<User> participants, int recurringCount) {
        super(title, owner, datetimeInterval, participants);
        this.recurringCount = recurringCount;
    }
}
