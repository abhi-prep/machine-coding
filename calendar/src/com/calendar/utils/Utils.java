package com.calendar.utils;

import com.calendar.model.intervals.DateTimeInterval;

import java.time.Duration;
import java.time.LocalDateTime;

public class Utils {
    public static boolean isMoreThanTMinutesBetween(DateTimeInterval interval1, DateTimeInterval interval2, int t) {
        LocalDateTime end1 = interval1.getEnd();
        LocalDateTime start2 = interval2.getStart();

        Duration duration = Duration.between(end1, start2.plusMinutes(1));
        long minutesBetween = duration.toMinutes();

        return minutesBetween > t;
    }

    public static int dateTimeIntervalComparator(DateTimeInterval a1, DateTimeInterval a2) {
        if (a1.getStart().isBefore(a2.getStart())) {
            return -1;
        } else if (a1.getStart().isEqual(a2.getStart())) {
            return 0;
        } else {
            return 1;
        }
    }
}
