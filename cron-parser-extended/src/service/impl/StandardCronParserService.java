package service.impl;

import exception.CronParseException;
import model.Command;
import model.CronExpression;
import service.CommandHandler;
import service.CronParserService;
import service.FieldParser;
import util.Constants;
import util.Utils;

import java.util.Arrays;

public class StandardCronParserService implements CronParserService {

    private final FieldParser fieldParser;
    private final CommandHandler commandHandler;

    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 59;
    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;
    private static final int MIN_DAY_OF_MONTH = 1;
    private static final int MAX_DAY_OF_MONTH = 31;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private static final int MIN_DAY_OF_WEEK = 1;
    private static final int MAX_DAY_OF_WEEK = 7;
    private static final int MIN_YEAR = 1970;
    private static final int MAX_YEAR = 2099;

    public StandardCronParserService(FieldParser fieldParser, CommandHandler commandHandler) {
        this.fieldParser = fieldParser;
        this.commandHandler = commandHandler;
    }

    @Override
    public CronExpression parse(String cronExpressionString) {
        if (cronExpressionString == null || cronExpressionString.trim().isEmpty()) {
            throw new CronParseException("Cron expression cannot be empty");
        }

        String[] tokens = cronExpressionString.trim().split("\\s+");
        if (tokens.length < 6) {
            throw new CronParseException("Invalid cron expression format: " + cronExpressionString);
        }

        CronExpression cronExpression = new CronExpression();
        try {
            // Parse minutes
            cronExpression.setMinutes(
                    fieldParser.parse(tokens[0].trim(), MIN_MINUTE, MAX_MINUTE, Constants.FieldType.MINUTE));

            // Parse hours
            cronExpression.setHours(fieldParser.parse(tokens[1].trim(), MIN_HOUR, MAX_HOUR, Constants.FieldType.HOUR));

            // Parse day of month
            cronExpression.setDaysOfMonth(fieldParser.parse(tokens[2].trim(), MIN_DAY_OF_MONTH, MAX_DAY_OF_MONTH,
                    Constants.FieldType.DAY_OF_MONTH));

            // Parse months
            cronExpression
                    .setMonths(fieldParser.parse(tokens[3].trim(), MIN_MONTH, MAX_MONTH, Constants.FieldType.MONTH));

            // Parse day of week
            cronExpression.setDaysOfWeek(fieldParser.parse(tokens[4].trim(), MIN_DAY_OF_WEEK, MAX_DAY_OF_WEEK,
                    Constants.FieldType.DAY_OF_WEEK));

            // Parse year
            if (Utils.isValidYear(tokens[5].trim())) {
                cronExpression
                        .setYears(fieldParser.parse(tokens[5].trim(), MIN_YEAR, MAX_YEAR, Constants.FieldType.YEAR));
                cronExpression.setCommand(commandHandler.parse(String.join(" ", Arrays.copyOfRange(tokens, 6, tokens.length))));
            } else {
                Command command = commandHandler.parse(String.join(" ", Arrays.copyOfRange(tokens, 5, tokens.length)));
                cronExpression.setCommand(command);
            }

        } catch (CronParseException e) {
            throw e;
        } catch (Exception e) {
            throw new CronParseException("Error parsing cron expression", e);
        }
        return cronExpression;
    }
}
