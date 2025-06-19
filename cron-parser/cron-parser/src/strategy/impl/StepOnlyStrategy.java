package strategy.impl;

import exception.CronParseException;
import strategy.CronFieldStrategy;

import java.util.List;
import java.util.stream.IntStream;

import static util.Utils.parseValue;

public class StepOnlyStrategy implements CronFieldStrategy {

    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("\\d+/\\d+");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max) {
        String[] parts = fieldValue.split("/");
        int start = parseValue(parts[0], min, max);
        int step = Integer.parseInt(parts[1]);

        if (step <= 0) {
            throw new CronParseException("Step must be > 0: " + fieldValue);
        }

        return IntStream.rangeClosed(start, max)
                .filter(i -> (i - start) % step == 0)
                .boxed()
                .toList();
    }
}
