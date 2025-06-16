package model;

import java.time.LocalTime;

public class ClassTimeValidator implements ClassValidator {
    @Override
    public boolean validate(Gym gym, GymClass gymClass) {
        LocalTime start = gymClass.getStartTime();
        LocalTime end = gymClass.getEndTime();
        if(start.isBefore(LocalTime.of(6, 0)) || end.isAfter(LocalTime.of(20, 0)) || !start.isBefore(end)){
            return false;
        }
        return true;
    }
}
