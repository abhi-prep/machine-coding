package service.impl;

import exception.CronParseException;
import model.CronExpression;
import service.CronParserService;
import service.FieldParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardCronParserService implements CronParserService {

    private final FieldParser fieldParser;

    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 59;
    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;
    private static final int MIN_DAY_OF_MONTH = 1;
    private static final int MAX_DAY_OF_MONTH = 31;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private static final int MIN_DAY_OF_WEEK = 0;
    private static final int MAX_DAY_OF_WEEK = 6;

    private static final Pattern CRON_PATTERN = Pattern.compile(
            "^\\s*([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+(.+)$"
    );
    public StandardCronParserService(FieldParser fieldParser) {
        this.fieldParser = fieldParser;
    }

    @Override
    public CronExpression parse(String cronExpressionString) {
        if(cronExpressionString == null || cronExpressionString.trim().isEmpty()){
            throw new CronParseException("Cron expression cannot be empty");
        }

        Matcher matcher = CRON_PATTERN.matcher(cronExpressionString);
        if(!matcher.matches()){
            throw new CronParseException("Invalid cron expression format: " + cronExpressionString);
        }

        CronExpression cronExpression = new CronExpression();

        try {
            // Parse minutes
            cronExpression.setMinutes(fieldParser.parse(matcher.group(1), MIN_MINUTE, MAX_MINUTE));

            // Parse hours
            cronExpression.setHours(fieldParser.parse(matcher.group(2), MIN_HOUR, MAX_HOUR));

            // Parse day of month
            cronExpression.setDaysOfMonth(fieldParser.parse(matcher.group(3), MIN_DAY_OF_MONTH, MAX_DAY_OF_MONTH));

            // Parse months
            cronExpression.setMonths(fieldParser.parse(matcher.group(4), MIN_MONTH, MAX_MONTH));

            // Parse day of week
            cronExpression.setDaysOfWeek(fieldParser.parse(matcher.group(5), MIN_DAY_OF_WEEK, MAX_DAY_OF_WEEK));

            cronExpression.setCommand(matcher.group(6).trim());

        } catch (CronParseException e){
            throw e;
        } catch (Exception e){
            throw new CronParseException("Error parsing cron expression", e);
        }
        return cronExpression;
    }
}
