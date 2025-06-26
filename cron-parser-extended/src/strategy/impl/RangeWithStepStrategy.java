package strategy.impl;

import exception.CronParseException;
import strategy.CronFieldStrategy;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static util.Utils.parseValue;

public class RangeWithStepStrategy implements CronFieldStrategy {

    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("([a-zA-Z]+|\\d+)-([a-zA-Z]+|\\d+)+/\\d+");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max, Constants.FieldType fieldType) {
        String[] parts = fieldValue.split("[/-]");
        int start = parseValue(parts[0], min, max, fieldType);
        int end = parseValue(parts[1], min, max, fieldType);
        int step = Integer.parseInt(parts[2]);

        if (step <= 0) {
            throw new CronParseException("Invalid range with step: " + fieldValue);
        }

        List<Integer> combined = new ArrayList<>();
        if (start > end) {
            List<Integer> part1 = IntStream.rangeClosed(start, max).boxed().toList();
            List<Integer> part2 = IntStream.rangeClosed(min, end).boxed().toList();
            combined.addAll(part1);
            combined.addAll(part2);
        } else {
            combined.addAll(IntStream.rangeClosed(start, end).boxed().toList());
        }

        return IntStream.rangeClosed(0, combined.size()-1)
                .filter(i -> i % step == 0)
                .map(i -> combined.get(i))
                .boxed()
                .toList();

    }
}
