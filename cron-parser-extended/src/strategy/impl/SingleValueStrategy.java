package strategy.impl;

import strategy.CronFieldStrategy;
import util.Constants;

import java.util.List;

import static util.Utils.parseValue;

public class SingleValueStrategy implements CronFieldStrategy {
    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("[a-zA-Z]+|\\d+");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max, Constants.FieldType fieldType) {
        int value = parseValue(fieldValue, min, max, fieldType);
        return List.of(value);
    }
}
