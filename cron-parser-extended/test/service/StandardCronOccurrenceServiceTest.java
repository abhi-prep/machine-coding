package service;

import model.Command;
import model.CronExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.StandardCronOccurrenceService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StandardCronOccurrenceServiceTest {

    private StandardCronOccurrenceService occurrenceService;

    @BeforeEach
    void setUp(){
        occurrenceService = new StandardCronOccurrenceService();
    }

    @Test
    void testInvalidParams(){
        CronExpression cronExpression = new CronExpression(
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                new Command.Builder("/usr/bin/find").build()
        );
        LocalDateTime startTime = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,  () -> occurrenceService.getNextOccurrences(null, null, -1 ));
        assertThrows(IllegalArgumentException.class,  () -> occurrenceService.getNextOccurrences(cronExpression, null, -1 ));
        assertThrows(IllegalArgumentException.class,  () -> occurrenceService.getNextOccurrences(cronExpression, startTime, -1 ));
    }
}
