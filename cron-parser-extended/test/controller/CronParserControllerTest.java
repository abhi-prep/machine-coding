package controller;

import exception.CronParseException;
import model.Command;
import model.CronExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import service.CronFormatterService;
import service.CronOccurrenceService;
import service.CronParserService;
import util.OccurrenceFormatter;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class CronParserControllerTest {

    @Mock
    private CronParserService parserService;

    @Mock
    private CronFormatterService formatterService;

    @Mock
    private CronOccurrenceService occurrenceService;

    private CronParserController controller;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        controller = new CronParserController(parserService, formatterService, occurrenceService);
    }

    @Test
    void testProcessCronExpression(){
        String cronExpressionString = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CronExpression mockCronExpression = new CronExpression(
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                new Command.Builder("/usr/bin/find").build()
        );
        String expectedOutput = "formatted output";

        when(parserService.parse(cronExpressionString)).thenReturn(mockCronExpression);
        when(formatterService.format(mockCronExpression)).thenReturn(expectedOutput);

        String result = controller.processCronExpression(cronExpressionString);

        assertEquals(expectedOutput, result);
        verify(parserService).parse(cronExpressionString);
        verify(formatterService).format(mockCronExpression);
    }

    @Test
    void testProcessCronExpressionWithParseException(){
        String cronExpressionString = "invalid";

        when(parserService.parse(cronExpressionString)).thenThrow(new CronParseException("invalid cron"));

        assertThrows(CronParseException.class, () -> controller.processCronExpression(cronExpressionString));

        verify(parserService).parse(cronExpressionString);
        verifyNoInteractions(formatterService);
    }

    @Test
    void testGetNextOccurrencesOutput(){
        String cronExpressionString = "*/15 5-14 1,15 * WED/2 /usr/bin/find";
        String formattedOutput = "formated next n occurrences";
        LocalDateTime startTime = LocalDateTime.now();

        CronExpression mockCronExpression = new CronExpression(
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                new Command.Builder("/usr/bin/find").build()
        );
        when(parserService.parse(cronExpressionString)).thenReturn(mockCronExpression);

        MockedStatic<OccurrenceFormatter> occurrenceFormatter = mockStatic(OccurrenceFormatter.class);
        occurrenceFormatter.when(() -> OccurrenceFormatter.formatOccurrences(anyList()))
                .thenReturn(formattedOutput);

        String result = controller.getNextOccurrencesOutput(cronExpressionString, startTime, 2);

        verify(parserService).parse(cronExpressionString);
        verify(occurrenceService).getNextOccurrences(mockCronExpression, startTime, 2);
        occurrenceFormatter.verify(() -> OccurrenceFormatter.formatOccurrences(anyList()), times(1));
        assertEquals(result, formattedOutput);

    }
}
