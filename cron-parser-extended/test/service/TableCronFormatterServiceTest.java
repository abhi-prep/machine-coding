package service;

import model.Command;
import model.CronExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.StandardCommandHandler;
import service.impl.TableCronFormatterService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TableCronFormatterServiceTest {

    private CronFormatterService formatterService;
    private CronExpression cronExpression;
    private CommandHandler commandHandler;

    @BeforeEach
    void setUp(){
        commandHandler = new StandardCommandHandler();
        formatterService = new TableCronFormatterService(commandHandler);

        cronExpression = new CronExpression();
        cronExpression.setMinutes(Arrays.asList(0,15,30,45));
        cronExpression.setHours(List.of(0));
        cronExpression.setDaysOfMonth(Arrays.asList(1,15));
        cronExpression.setMonths(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
        cronExpression.setDaysOfWeek(Arrays.asList(1,2,3,4,5));
        cronExpression.setCommand(new Command.Builder("/usr/bin/find").build());

    }

    @Test
    void testformat(){
        String expectedOutput =
                "minute         0 15 30 45\n" +
                "hour           0\n" +
                "day of month   1 15\n" +
                "month          1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week    1 2 3 4 5\n" +
                "command        Name: /usr/bin/find | Args: \n";

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
        emptyExpression.setCommand(new Command.Builder("/usr/bin/find").build());

        String expectedOutput =
                "minute         \n" +
                "hour           \n" +
                "day of month   \n" +
                "month          \n" +
                "day of week    \n" +
                "command        Name: /usr/bin/find | Args: \n";

        String formattedOutput = formatterService.format(emptyExpression);
        assertEquals(expectedOutput, formattedOutput);
    }

    @Test
    void testFormatWithCommandWithArgs(){
        Command command = new Command
                .Builder("/usr/bin/find")
                .args(new HashMap<>(){{put("-arg1", "1"); put("-arg2", "2");}})
                .build();
        cronExpression.setCommand(command);
        String formattedOutput = formatterService.format(cronExpression);
        assertTrue(formattedOutput.contains("command        Name: /usr/bin/find | Args: -arg2=2 -arg1=1"));
    }
}
