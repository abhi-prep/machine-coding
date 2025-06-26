package service.impl;

import model.CronExpression;
import service.CronOccurrenceService;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StandardCronOccurrenceService implements CronOccurrenceService {
    @Override
    public List<LocalDateTime> getNextOccurrences(CronExpression cronExpression, LocalDateTime startTime, int count) {

        if(cronExpression == null){
            throw new IllegalArgumentException("CronExpression cannot be null");
        }

        if(startTime == null){
            throw new IllegalArgumentException("startTime cannot be null");
        }

        if(count <= 0){
            throw new IllegalArgumentException("Count must be positive");
        }

        List<LocalDateTime> occurrences = new ArrayList<>(count);

        List<Integer> years = cronExpression.getYears() == null ? IntStream.rangeClosed(startTime.getYear(), 2099).boxed().toList(): cronExpression.getYears();
        OUTER:
        for(int y: years){
            for (int m: cronExpression.getMonths()){
                YearMonth yearMonth = YearMonth.of(y, m);
                for (int dom: cronExpression.getDaysOfMonth()){
                    if(yearMonth.isValidDay(dom)){
                        for (int h: cronExpression.getHours()){
                            for (int min: cronExpression.getMinutes()){
                                LocalDateTime dt = LocalDateTime.of(y, m, dom, h, min);
                                if(cronExpression.getDaysOfWeek().contains(dt.getDayOfWeek().getValue()) && dt.isAfter(startTime)){
                                    occurrences.add(dt);
                                    if(occurrences.size() == count){
                                        break OUTER;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return occurrences;
    }
}
