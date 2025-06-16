package com.calendar.model.intervals;

import java.time.LocalDateTime;

public class DateTimeInterval implements Interval<LocalDateTime>{

    private LocalDateTime start;
    private LocalDateTime end;

    public DateTimeInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.start = startDateTime;
        this.end = endDateTime;
    }

    @Override
    public LocalDateTime getStart() {
        return start;
    }

    @Override
    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public void setStart(LocalDateTime start) {
        this.start = start;

    }

    @Override
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "DateTimeInterval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}