package com.calendar.model.intervals;

public interface Interval<K> {
    K getStart();
    K getEnd();
    void setStart(K start);
    void setEnd(K end);
}
