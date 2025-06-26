package strategy.impl;

import exception.CronParseException;
import strategy.CronFieldStrategy;
import util.Constants;

import java.util.List;
import java.util.stream.IntStream;

import static util.Utils.parseValue;

public class StepOnlyStrategy implements CronFieldStrategy {

    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("([a-zA-Z]+|\\d+)/\\d+");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max, Constants.FieldType fieldType) {
        String[] parts = fieldValue.split("/");
        int start = parseValue(parts[0], min, max, fieldType);
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
