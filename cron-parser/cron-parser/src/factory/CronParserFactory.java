package factory;

import controller.CronParserController;
import service.CronFormatterService;
import service.CronParserService;
import service.FieldParser;
import service.impl.StandardCronParserService;
import service.impl.StandardFieldParser;
import service.impl.TableCronFormatterService;

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


    // private constructor for singleton pattern
    private CronParserFactory() {
        this.fieldParser = new StandardFieldParser();
        this.parserService = new StandardCronParserService(fieldParser);
        this.formatterService = new TableCronFormatterService();
        this.controller = new CronParserController(parserService, formatterService);
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
}
