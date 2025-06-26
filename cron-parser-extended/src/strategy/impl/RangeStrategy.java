package strategy.impl;

import strategy.CronFieldStrategy;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static util.Utils.parseValue;

public class RangeStrategy implements CronFieldStrategy {
    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("([a-zA-Z]+|\\d+)-([a-zA-Z]+|\\d+)");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max, Constants.FieldType fieldType) {
        String[] parts = fieldValue.split("-");
        int start = parseValue(parts[0], min, max, fieldType);
        int end = parseValue(parts[1], min, max, fieldType);

        if (start > end) {
            List<Integer> part1 = IntStream.rangeClosed(start, max).boxed().toList();
            List<Integer> part2 = IntStream.rangeClosed(min, end).boxed().toList();
            List<Integer> combined = new ArrayList<>();
            combined.addAll(part1);
            combined.addAll(part2);
            return combined;
        }

        return IntStream.rangeClosed(start, end).boxed().toList();
    }
}
