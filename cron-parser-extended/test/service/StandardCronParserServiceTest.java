package service;

import exception.CronParseException;
import model.Command;
import model.CronExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.impl.StandardCronParserService;
import util.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class StandardCronParserServiceTest {

    private CronParserService cronParserService;

    @Mock
    private FieldParser fieldParser;

    @Mock
    CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cronParserService = new StandardCronParserService(fieldParser, commandHandler);

        // Setup common mock behavior
        when(fieldParser.parse(eq("0"), anyInt(), anyInt(), any(Constants.FieldType.class))).thenReturn(List.of(0));
        when(fieldParser.parse(eq("*/15"), anyInt(), anyInt(), any(Constants.FieldType.class))).thenReturn(Arrays.asList(0, 15, 30, 45));
        when(fieldParser.parse(eq("1,15"), anyInt(), anyInt(), any(Constants.FieldType.class))).thenReturn(Arrays.asList(1, 15));
        when(fieldParser.parse(eq("*"), anyInt(), anyInt(), any(Constants.FieldType.class))).thenReturn(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        when(fieldParser.parse(eq("1-5"), anyInt(), anyInt(), any(Constants.FieldType.class))).thenReturn(Arrays.asList(1, 2, 3, 4, 5));

        Command command = new Command.Builder("/usr/bin/find").build();
        when(commandHandler.parse(eq("/usr/bin/find"))).thenReturn(command);
        command.getArgs().put("-name","test");
        when(commandHandler.parse(eq("/usr/bin/find -name test"))).thenReturn(command);
    }

    @Test
    void testParseValidCronExpression() {
        String cronExpressionString = "*/15 0 1,15 * 1-5 /usr/bin/find";

        CronExpression result = cronParserService.parse(cronExpressionString);

        assertNotNull(result);
        assertEquals(Arrays.asList(0, 15, 30, 45), result.getMinutes());
        assertEquals(List.of(0), result.getHours());
        assertEquals(Arrays.asList(1, 15), result.getDaysOfMonth());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), result.getMonths());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), result.getDaysOfWeek());
        assertEquals("/usr/bin/find", result.getCommand().getName());

        // Verify that parser was called for each field
        verify(fieldParser).parse(eq("*/15"), eq(0), eq(59), eq(Constants.FieldType.MINUTE));
        verify(fieldParser).parse(eq("0"), eq(0), eq(23), eq(Constants.FieldType.HOUR));
        verify(fieldParser).parse(eq("1,15"), eq(1), eq(31), eq(Constants.FieldType.DAY_OF_MONTH));
        verify(fieldParser).parse(eq("*"), eq(1), eq(12), eq(Constants.FieldType.MONTH));
        verify(fieldParser).parse(eq("1-5"), eq(1), eq(7), eq(Constants.FieldType.DAY_OF_WEEK));
    }

    @Test
    void testParseEmptyCronExpression() {
        assertThrows(CronParseException.class, () -> cronParserService.parse(""));
    }

    @Test
    void testParseNullCronExpression() {
        assertThrows(CronParseException.class, () -> cronParserService.parse(null));
    }

    @Test
    void testParseInvalidFormatCronExpression() {
        assertThrows(CronParseException.class, () -> cronParserService.parse("not a valid cron expression"));
    }

    @Test
    void testParseInvalidFieldThrowsException() {
        when(fieldParser.parse(eq("invalid"), anyInt(), anyInt(), any(Constants.FieldType.class)))
                .thenThrow(new CronParseException("Invalid field"));

        assertThrows(CronParseException.class, () ->
                cronParserService.parse("invalid 0 1 * * /usr/bin/find"));
    }

    @Test
    void testParseWithTooFewFields() {
        assertThrows(CronParseException.class, () ->
                cronParserService.parse("* * * *"));
    }

    @Test
    void testParseCommandWithSpaces() {
        String cronExpressionString = "*/15 0 1,15 * 1-5 /usr/bin/find -name test";

        CronExpression result = cronParserService.parse(cronExpressionString);

        Command command = new Command.Builder("/usr/bin/find").args(new HashMap<>(){{put("-name","test");}}).build();
        assertEquals(command.getName(), result.getCommand().getName());
    }
}