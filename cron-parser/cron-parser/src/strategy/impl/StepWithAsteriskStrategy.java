package strategy.impl;

import exception.CronParseException;
import strategy.CronFieldStrategy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StepWithAsteriskStrategy implements CronFieldStrategy {
    @Override
    public boolean matches(String fieldValue) {
        return fieldValue.matches("\\*/\\d+");
    }

    @Override
    public List<Integer> parse(String fieldValue, int min, int max) {
        int step = Integer.parseInt(fieldValue.substring(2));
        if(step <= 0){
            throw new CronParseException("Step value must be positive: " + fieldValue);
        }
        return IntStream.rangeClosed(min, max)
                .filter(i -> (i-min)%step == 0)
                .boxed()
                .collect(Collectors.toList());
    }
}
