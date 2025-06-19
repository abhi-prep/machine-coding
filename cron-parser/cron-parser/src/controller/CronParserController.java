package controller;

import model.CronExpression;
import service.CronFormatterService;
import service.CronParserService;

/**
 * Controller for the cron parsing application
 */
public class CronParserController {
    private final CronParserService parserService;
    private final CronFormatterService formatterService;

    public CronParserController(CronParserService parserService, CronFormatterService formatterService) {
        this.parserService = parserService;
        this.formatterService = formatterService;
    }

    public String processCronExpression(String cronExpressionString){
        CronExpression cronExpression = parserService.parse(cronExpressionString);
        return formatterService.format(cronExpression);
    }
}
