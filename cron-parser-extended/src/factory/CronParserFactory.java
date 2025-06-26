package factory;

import controller.CronParserController;
import service.*;
import service.impl.*;

/**
 * Factory class for creating instances of services and controllers
 * Implements the factory design pattern for dependency management
 */
public class CronParserFactory {
    private static CronParserFactory instance;

    private final FieldParser fieldParser;
    private final CronParserService parserService;
    private final CronFormatterService formatterService;
    private final CronParserController controller;
    private final CronOccurrenceService cronOccurrenceService;
    private final CommandHandler commandHandler;


    // private constructor for singleton pattern
    private CronParserFactory() {
        this.fieldParser = new StandardFieldParser();
        this.commandHandler = new StandardCommandHandler();
        this.parserService = new StandardCronParserService(fieldParser, commandHandler);
        this.formatterService = new TableCronFormatterService(commandHandler);
        this.cronOccurrenceService = new StandardCronOccurrenceService();
        this.controller = new CronParserController(parserService, formatterService, cronOccurrenceService);
    }

    public static CronParserFactory getInstance() {
        if(instance == null){
            instance = new CronParserFactory();
        }
        return instance;
    }

    public FieldParser getFieldParser() {
        return fieldParser;
    }

    public CronParserService getParserService() {
        return parserService;
    }

    public CronFormatterService getFormatterService() {
        return formatterService;
    }

    public CronParserController getController() {
        return controller;
    }

    public CronOccurrenceService getCronOccurrenceService(){
        return cronOccurrenceService;
    }
}
