package com.calendar.service;

import com.calendar.model.User;
import com.calendar.model.events.Event;
import com.calendar.model.intervals.DateTimeInterval;
import com.calendar.model.intervals.TimeInterval;
import com.calendar.utils.Utils;

import java.time.LocalDateTime;
import java.util.*;

public class UserService {

    private Map<String, User> users;

    public UserService() {
        this.users = new HashMap<>();
    }

    public void registerUser(String username, String password, TimeInterval workingHours) {
        User newUser = new User(username, password, workingHours);
        users.put(newUser.getUsername(),newUser);
        System.out.println("User registered: " + username);
    }

    public User loginUser(String username, String password) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("User logged in: " + username);
                return user;
            }
        }
        System.out.println("User not found: " + username);
        return null;
    }

    public User getUserByUsername(String username) {
        return users.getOrDefault(username, null);
    }

    public Set<Event> fetchConflictingEvents(User user) {
        List<Event> userEvents = user.getEvents();
        Set<Event> conflictingEvents = new HashSet<>();

        if(user.getEvents().size() < 2){
            System.out.println("No conflicting events");
            return conflictingEvents;
        }

        userEvents.sort((a1, a2) -> {
            DateTimeInterval a1DatetimeInterval = a1.getDatetimeInterval();
            DateTimeInterval a2DatetimeInterval = a2.getDatetimeInterval();
            return Utils.dateTimeIntervalComparator(a1DatetimeInterval, a2DatetimeInterval);
        });

        for (int i = 1; i < userEvents.size(); i++) {
            Event event1 = userEvents.get(i-1);
            Event event2 = userEvents.get(i);
            if(event1.getDatetimeInterval().getEnd().isAfter(event2.getDatetimeInterval().getStart())){
                conflictingEvents.add(event1);
                conflictingEvents.add(event2);
            }
        }

        return conflictingEvents;
    }

    public DateTimeInterval findFavorableSlot(Set<User> users, int durationInMinutes) {

        List<DateTimeInterval> intervals = new ArrayList<>();
        for (User user: users) {
            for (Event event: user.getEvents()) {
                intervals.add(event.getDatetimeInterval());
            }
        }

        intervals.sort(Utils::dateTimeIntervalComparator);

        // Merging intervals
        Stack<DateTimeInterval> stack = new Stack<>();
        for(int i=0; i<intervals.size(); i++){
            if(stack.isEmpty()){
                stack.push(intervals.get(i));
            }
            else{
                DateTimeInterval top = stack.pop();
                DateTimeInterval current = intervals.get(i);
                if(top.getEnd().isAfter(current.getStart())){
                    LocalDateTime end = top.getEnd().isAfter(current.getEnd())? top.getEnd():current.getEnd();
                    top.setEnd(end);
                    stack.push(top);
                }
                else{
                    stack.push(top);
                    stack.push(current);
                }
            }
        }

        intervals = new ArrayList<>();
        while (!stack.isEmpty()){
            DateTimeInterval top = stack.pop();
            intervals.add(top);
        }

        intervals.sort(Utils::dateTimeIntervalComparator);

        for(int i=1; i<intervals.size(); i++){
            if(Utils.isMoreThanTMinutesBetween(intervals.get(i-1), intervals.get(i), durationInMinutes)){
                return new DateTimeInterval(intervals.get(i-1).getEnd(), intervals.get(i).getStart());
            }
        }
        return new DateTimeInterval(intervals.get(intervals.size()-1).getEnd(), intervals.get(intervals.size()-1).getEnd().plusMinutes(durationInMinutes));
    }
}
