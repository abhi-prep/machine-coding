package strategy.impl;

import exception.CronParseException;
import strategy.CronFieldStrategy;
import util.Constants;

import java.util.ArrayList;
import java.util.List;


import java.util.*;

public class CommaSeparatedStrategy implements CronFieldStrategy {

    private final List<CronFieldStrategy> subStrategies = List.of(
            new AsteriskStrategy(),
            new StepWithAsteriskStrategy(),
            new RangeWithStepStrategy(),
            new RangeStrategy(),
            new StepOnlyStrategy(),
            new SingleValueStrategy()
    );

    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.contains(",");
    }

    @Override
    public List<Integer> parse(String field, int min, int max, Constants.FieldType fieldType) {
        String[] parts = field.split(",");
        Set<Integer> resultSet = new TreeSet<>(); // Sorted + unique

        for (String part : parts) {
            CronFieldStrategy strategy = subStrategies.stream()
                    .filter(s -> s.matches(part))
                    .findFirst()
                    .orElseThrow(() -> new CronParseException("Unsupported cron part: " + part));

            resultSet.addAll(strategy.parse(part, min, max, fieldType));
        }

        return new ArrayList<>(resultSet);
    }
}
