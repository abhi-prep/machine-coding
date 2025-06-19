package service.impl;

import exception.CronParseException;
import service.FieldParser;
import strategy.CronFieldStrategy;
import strategy.impl.*;

import java.util.List;

/**
 * Implementation of FieldParser that handles standard cron field expressions
 * including asterisks, ranges, steps, and value lists
 */
public class StandardFieldParser implements FieldParser {

    private final List<CronFieldStrategy> strategies = List.of(
            new AsteriskStrategy(),
            new StepWithAsteriskStrategy(),
            new CommaSeparatedStrategy(), // recursive
            new RangeWithStepStrategy(),
            new RangeStrategy(),
            new StepOnlyStrategy(),
            new SingleValueStrategy()
    );

    @Override
    public List<Integer> parse(String fieldValue, int min, int max) {
        if(fieldValue == null || fieldValue.trim().isEmpty()){
            throw new CronParseException("Field value cannot be empty");
        }
        return strategies.stream()
                .filter(s -> s.matches(fieldValue))
                .findFirst()
                .orElseThrow(() -> new CronParseException("Unsupported cron format: " + fieldValue))
                .parse(fieldValue, min, max);
    }

}
