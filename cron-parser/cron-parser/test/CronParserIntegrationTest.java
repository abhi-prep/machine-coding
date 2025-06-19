import controller.CronParserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.CronFormatterService;
import service.CronParserService;
import service.impl.StandardCronParserService;
import service.impl.StandardFieldParser;
import service.impl.TableCronFormatterService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CronParserIntegrationTest {
    private CronParserController controller;

    @BeforeEach
    void setUp(){
        StandardFieldParser fieldParser = new StandardFieldParser();
        CronParserService parserService = new StandardCronParserService(fieldParser);
        CronFormatterService formatterService = new TableCronFormatterService();
        controller = new CronParserController(parserService, formatterService);
    }

    @Test
    void testStandardCronExpression(){
        String cronExpression = "*/15 0 1,15 * 1-5 /usr/bin/find";

        String result = controller.processCronExpression(cronExpression);

        assertNotNull(result);
        assertTrue(result.contains("minute         0 15 30 45"));
        assertTrue(result.contains("hour           0"));
        assertTrue(result.contains("day of month   1 15"));
        assertTrue(result.contains("month          1 2 3 4 5 6 7 8 9 10 11 12"));
        assertTrue(result.contains("day of week    1 2 3 4 5"));
        assertTrue(result.contains("command        /usr/bin/find"));

    }

    @Test
    void testEveryMinuteExpression(){
        String cronExpression = "* * * * * /usr/bin/echo hello";

        String result = controller.processCronExpression(cronExpression);
        assertTrue(result.contains("minute         0 1 2 3 4 5 6 7 8 9 10"));
        assertTrue(result.contains("command        /usr/bin/echo hello"));
    }

    @ParameterizedTest
    @MethodSource("provideValidCronExpressions")
    void testVariousCronExpressions(String cronExpression){
        assertDoesNotThrow(() -> controller.processCronExpression(cronExpression));
    }

    private static Stream<Arguments> provideValidCronExpressions(){
        return Stream.of(
            Arguments.of("0 0 * * * /daily/backup.sh"),
            Arguments.of("*/5 * * * * /check/service.sh"),
            Arguments.of("0 12 * * 1-5 /usr/bin/lunch-reminder"),
            Arguments.of("0 0 1 */3 * /usr/bin/quarterly-report"),
            Arguments.of("0 */2 * * * /usr/bin/biHourlyJob"),
            Arguments.of("30 4 1,15 * 5 /home/user/first-last-friday")
        );
    }
}
