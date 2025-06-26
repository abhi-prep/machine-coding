import org.junit.jupiter.api.Test;
import factory.CronParserFactory;

import static org.junit.jupiter.api.Assertions.*;

public class CronParserApplicationTest {
    @Test
    void testMainMethodWithValidInput(){
        String[] args = {"*/15 0 1,15 * 1-5 /usr/bin/find"};
        assertDoesNotThrow(() -> CronParserApplication.main(args));
    }

    @Test
    void testFactorySingletonInstanceIsCreatedSuccessfully(){
        CronParserFactory factory = CronParserFactory.getInstance();
        assertTrue(factory.getController() != null);
        assertTrue(factory.getParserService() != null);
        assertTrue(factory.getFormatterService() != null);
        assertTrue(factory.getFieldParser() != null);
        assertTrue(factory.getCronOccurrenceService() != null);
    }
}
