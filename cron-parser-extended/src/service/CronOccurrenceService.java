package service;

import model.CronExpression;

import java.time.LocalDateTime;
import java.util.List;

public interface CronOccurrenceService {
    List<LocalDateTime> getNextOccurrences(CronExpression cronExpression, LocalDateTime startTime, int count);
}
