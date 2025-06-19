package strategy.impl;

import exception.CronParseException;
import strategy.CronFieldStrategy;

import java.util.List;
import java.util.stream.IntStream;

import static util.Utils.parseValue;

public class RangeWithStepStrategy implements CronFieldStrategy {

    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("\\d+-\\d+/\\d+");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max) {
        String[] parts = fieldValue.split("[/-]");
        int start = parseValue(parts[0], min, max);
        int end = parseValue(parts[1], min, max);
        int step = Integer.parseInt(parts[2]);

        if (step <= 0 || start > end) {
            throw new CronParseException("Invalid range with step: " + fieldValue);
        }

        return IntStream.rangeClosed(start, end)
                .filter(i -> (i - start) % step == 0)
                .boxed()
                .toList();
    }
}
