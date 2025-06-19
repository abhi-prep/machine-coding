package controller;

import exception.CronParseException;
import model.CronExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.CronFormatterService;
import service.CronParserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class CronParserControllerTest {

    @Mock
    private CronParserService parserService;

    @Mock
    private CronFormatterService formatterService;

    private CronParserController controller;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        controller = new CronParserController(parserService, formatterService);
    }

    @Test
    void testProcessCronExpression(){
        String cronExpressionString = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CronExpression mockCronExpression = new CronExpression();
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
}
