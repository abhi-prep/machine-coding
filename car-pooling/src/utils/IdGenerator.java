package utils;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final AtomicLong counter = new AtomicLong(1);
    public static String nextId(String prefix) {
        return prefix + counter.getAndIncrement();
    }
}