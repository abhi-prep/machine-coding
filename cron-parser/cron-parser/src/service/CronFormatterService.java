package service;

import model.CronExpression;

/**
 * Service interface for formatting cron expressions as text output
 */
public interface CronFormatterService {
    String format(CronExpression cronExpression);
}
