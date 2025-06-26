package controller;

import model.CronExpression;
import service.CronFormatterService;
import service.CronOccurrenceService;
import service.CronParserService;
import util.OccurrenceFormatter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for the cron parsing application
 */
public class CronParserController {
    private final CronParserService parserService;
    private final CronFormatterService formatterService;
    private final CronOccurrenceService occurrenceService;

    public CronParserController(CronParserService parserService, CronFormatterService formatterService, CronOccurrenceService occurrenceService) {
        this.parserService = parserService;
        this.formatterService = formatterService;
        this.occurrenceService = occurrenceService;
    }

    public String processCronExpression(String cronExpressionString){
        CronExpression cronExpression = parserService.parse(cronExpressionString);
        return formatterService.format(cronExpression);
    }

    public String getNextOccurrencesOutput(String cronExpressionString, LocalDateTime startTime, int count) {
        CronExpression cronExpression = parserService.parse(cronExpressionString);
        List<LocalDateTime> occurrences = occurrenceService.getNextOccurrences(cronExpression, startTime, count);
        return OccurrenceFormatter.formatOccurrences(occurrences);
    }
}
