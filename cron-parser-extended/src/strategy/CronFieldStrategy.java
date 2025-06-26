package strategy;

import util.Constants;

import java.util.List;

public interface CronFieldStrategy {
    boolean matches(String fieldValue);
    List<Integer> parse(String fieldValue, int min, int max, Constants.FieldType fieldType);
}