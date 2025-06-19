package strategy;

import java.util.List;

public interface CronFieldStrategy {
    boolean matches(String fieldValue);
    List<Integer> parse(String fieldValue, int min, int max);
}