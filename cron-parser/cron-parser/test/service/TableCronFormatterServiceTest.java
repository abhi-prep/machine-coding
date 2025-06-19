package service;

import model.CronExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.TableCronFormatterService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableCronFormatterServiceTest {

    private CronFormatterService formatterService;
    private CronExpression cronExpression;

    @BeforeEach
    void setUp(){
        formatterService = new TableCronFormatterService();

        cronExpression = new CronExpression();
        cronExpression.setMinutes(Arrays.asList(0,15,30,45));
        cronExpression.setHours(Arrays.asList(0));
        cronExpression.setDaysOfMonth(Arrays.asList(1,15));
        cronExpression.setMonths(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
        cronExpression.setDaysOfWeek(Arrays.asList(1,2,3,4,5));
        cronExpression.setCommand("/usr/bin/find");

    }

    @Test
    void testformat(){
        String expectedOutput =
                "minute         0 15 30 45\n" +
                "hour           0\n" +
                "day of month   1 15\n" +
                "month          1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week    1 2 3 4 5\n" +
                "command        /usr/bin/find\n";

        String formattedOutput = formatterService.format(cronExpression);
        assertEquals(expectedOutput, formattedOutput);
    }

    @Test
    void testFormatWithNullExpressionThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> formatterService.format(null));
    }

    @Test
    void testFormatWithEmptyLists(){
        CronExpression emptyExpression = new CronExpression();
        emptyExpression.setMinutes(List.of());
        emptyExpression.setHours(List.of());
        emptyExpression.setDaysOfMonth(List.of());
        emptyExpression.setMonths(List.of());
        emptyExpression.setDaysOfWeek(List.of());
        emptyExpression.setCommand("");

        String expectedOutput =
                "minute         \n" +
                "hour           \n" +
                "day of month   \n" +
                "month          \n" +
                "day of week    \n" +
                "command        \n";

        String formattedOutput = formatterService.format(emptyExpression);
        assertEquals(expectedOutput, formattedOutput);
    }

    @Test
    void testFormatWithLongCommand(){
        cronExpression.setCommand("/usr/bin/find /home -name \"*.txt\" -exec grep pattern {} \\;");
        String formattedOutput = formatterService.format(cronExpression);
        assertTrue(formattedOutput.contains("command        /usr/bin/find /home -name \"*.txt\" -exec grep pattern {} \\;"));
    }
}
