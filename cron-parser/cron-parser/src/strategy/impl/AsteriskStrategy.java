package strategy.impl;

import strategy.CronFieldStrategy;

import java.util.List;
import java.util.stream.IntStream;

public class AsteriskStrategy implements CronFieldStrategy {
    @Override
    public boolean matches(String fieldValue) {
        return "*".equals(fieldValue);
    }
    @Override
    public List<Integer> parse(String fieldValue, int min, int max) {
        return IntStream.rangeClosed(min, max).boxed().toList();
    }
}
