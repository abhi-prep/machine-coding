package model;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Gym {
    private final int id;
    private final String name;
    private final String location;
    private final int maxAccommodation;
    private final Map<Integer, GymClass> classes = new ConcurrentHashMap<>();

    public Gym(int id, String name, String location, int maxAccommodation) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.maxAccommodation = maxAccommodation;
    }

    public int getMaxAccommodation() {
        return maxAccommodation;
    }

    public Map<Integer, GymClass> getClasses() {
        return classes;
    }

    public void addClass(GymClass cls) {
        classes.put(cls.getId(), cls);
    }

    public int getTotalClassLimitDuring(LocalTime start, LocalTime end) {
        int total = 0;
        for (GymClass cls : classes.values()) {
            if (cls.overlapsWith(start, end)) {
                total += cls.getMaxLimit();
            }
        }
        return total;
    }
}
