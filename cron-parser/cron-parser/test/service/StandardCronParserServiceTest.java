package service;

import exception.CronParseException;
import model.CronExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.impl.StandardCronParserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class StandardCronParserServiceTest {

    private CronParserService cronParserService;

    @Mock
    private FieldParser fieldParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cronParserService = new StandardCronParserService(fieldParser);

        // Setup common mock behavior
        when(fieldParser.parse(eq("0"), anyInt(), anyInt())).thenReturn(List.of(0));
        when(fieldParser.parse(eq("*/15"), anyInt(), anyInt())).thenReturn(Arrays.asList(0, 15, 30, 45));
        when(fieldParser.parse(eq("1,15"), anyInt(), anyInt())).thenReturn(Arrays.asList(1, 15));
        when(fieldParser.parse(eq("*"), anyInt(), anyInt())).thenReturn(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        when(fieldParser.parse(eq("1-5"), anyInt(), anyInt())).thenReturn(Arrays.asList(1, 2, 3, 4, 5));
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
        assertEquals("/usr/bin/find", result.getCommand());

        // Verify that parser was called for each field
        verify(fieldParser).parse(eq("*/15"), eq(0), eq(59));
        verify(fieldParser).parse(eq("0"), eq(0), eq(23));
        verify(fieldParser).parse(eq("1,15"), eq(1), eq(31));
        verify(fieldParser).parse(eq("*"), eq(1), eq(12));
        verify(fieldParser).parse(eq("1-5"), eq(0), eq(6));
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
        when(fieldParser.parse(eq("invalid"), anyInt(), anyInt()))
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

        assertEquals("/usr/bin/find -name test", result.getCommand());
    }
}