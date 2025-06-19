package service.impl;

import model.CronExpression;
import service.CronFormatterService;

import java.util.List;
import java.util.stream.Collectors;

public class TableCronFormatterService implements CronFormatterService {

    private static final int FIELD_NAME_WIDTH = 14;
    private static final String FIELD_MINUTE = "minute";
    private static final String FIELD_HOUR = "hour";
    private static final String FIELD_DAY_OF_MONTH = "day of month";
    private static final String FIELD_MONTH = "month";
    private static final String FIELD_DAY_OF_WEEK = "day of week";
    private static final String FIELD_COMMAND = "command";

    @Override
    public String format(CronExpression cronExpression) {
        if(cronExpression == null){
            throw new IllegalArgumentException("CronExpression cannot be null");
        }

        StringBuilder output = new StringBuilder();

        // Format minutes
        output.append(formatField(FIELD_MINUTE, cronExpression.getMinutes()));

        // Format hours
        output.append(formatField(FIELD_HOUR, cronExpression.getHours()));

        // Format day of month
        output.append(formatField(FIELD_DAY_OF_MONTH, cronExpression.getDaysOfMonth()));

        // Format month
        output.append(formatField(FIELD_MONTH, cronExpression.getMonths()));

        // Format day of week
        output.append(formatField(FIELD_DAY_OF_WEEK, cronExpression.getDaysOfWeek()));

        // Format command
        output.append(String.format("%-" + FIELD_NAME_WIDTH + "s %s%n", FIELD_COMMAND, cronExpression.getCommand()));

        return output.toString();
    }

    private String formatField(String fieldName, List<Integer> values){
        String valuesStr = values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        return String.format("%-" + FIELD_NAME_WIDTH + "s %s%n", fieldName, valuesStr);
    }
}
