package com.calendar.model.intervals;

import java.time.LocalTime;

public class TimeInterval implements Interval<LocalTime>{

    private LocalTime start;
    private LocalTime end;

    public TimeInterval(LocalTime startTime, LocalTime endTime) {
        this.start = startTime;
        this.end = endTime;
    }

    public boolean has(LocalTime timeToCheck){
        return !timeToCheck.isBefore(start) && !timeToCheck.isAfter(end);
    }

    @Override
    public LocalTime getStart() {
        return start;
    }

    @Override
    public LocalTime getEnd() {
        return end;
    }

    @Override
    public void setStart(LocalTime start) {
        this.start = start;
    }

    @Override
    public void setEnd(LocalTime end) {
        this.end = end;
    }
}