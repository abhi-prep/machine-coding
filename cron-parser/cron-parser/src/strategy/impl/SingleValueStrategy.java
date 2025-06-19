package strategy.impl;

import strategy.CronFieldStrategy;

import java.util.List;

import static util.Utils.parseValue;

public class SingleValueStrategy implements CronFieldStrategy {
    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("\\d+");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max) {
        int value = parseValue(fieldValue, min, max);
        return List.of(value);
    }
}
