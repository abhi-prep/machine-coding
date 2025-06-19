package strategy.impl;

import exception.CronParseException;
import strategy.CronFieldStrategy;

import java.util.List;
import java.util.stream.IntStream;

import static util.Utils.parseValue;

public class RangeStrategy implements CronFieldStrategy {
    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("\\d+-\\d+");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max) {
        String[] parts = fieldValue.split("-");
        int start = parseValue(parts[0], min, max);
        int end = parseValue(parts[1], min, max);

        if (start > end) {
            throw new CronParseException("Invalid range: " + fieldValue);
        }

        return IntStream.rangeClosed(start, end).boxed().toList();
    }
}
