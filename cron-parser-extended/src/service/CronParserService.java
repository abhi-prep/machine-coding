package service;

import model.CronExpression;

public interface CronParserService {
    CronExpression parse(String cronExpressionString);
}
