package util;

import exception.CronParseException;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Map<String, Integer> DAY_NAMES = new HashMap<>();
    private static final Map<String, Integer> MONTH_NAMES = new HashMap<>();

    static {
        DAY_NAMES.put("MON", 1);
        DAY_NAMES.put("MONDAY", 1);
        DAY_NAMES.put("TUE", 2);
        DAY_NAMES.put("TUESDAY", 2);
        DAY_NAMES.put("WED", 3);
        DAY_NAMES.put("WEDNESDAY", 3);
        DAY_NAMES.put("THU", 4);
        DAY_NAMES.put("THURSDAY", 4);
        DAY_NAMES.put("FRI", 5);
        DAY_NAMES.put("FRIDAY", 5);
        DAY_NAMES.put("SAT", 6);
        DAY_NAMES.put("SATURDAY", 6);
        DAY_NAMES.put("SUN", 7);
        DAY_NAMES.put("SUNDAY", 7);
    }

    static {
        MONTH_NAMES.put("JAN", 1);
        MONTH_NAMES.put("JANUARY", 1);
        MONTH_NAMES.put("FEB", 2);
        MONTH_NAMES.put("FEBRUARY", 2);
        MONTH_NAMES.put("MAR", 3);
        MONTH_NAMES.put("MARCH", 3);
        MONTH_NAMES.put("APR", 4);
        MONTH_NAMES.put("APRIL", 4);
        MONTH_NAMES.put("MAY", 5);
        MONTH_NAMES.put("JUN", 6);
        MONTH_NAMES.put("JUNE", 6);
        MONTH_NAMES.put("JUL", 7);
        MONTH_NAMES.put("JULY", 7);
        MONTH_NAMES.put("AUG", 8);
        MONTH_NAMES.put("AUGUST", 8);
        MONTH_NAMES.put("SEP", 9);
        MONTH_NAMES.put("SEPTEMBER", 9);
        MONTH_NAMES.put("OCT", 10);
        MONTH_NAMES.put("OCTOBER", 10);
        MONTH_NAMES.put("NOV", 11);
        MONTH_NAMES.put("NOVEMBER", 11);
        MONTH_NAMES.put("DEC", 12);
        MONTH_NAMES.put("DECEMBER", 12);
    }

    public static int parseValue(String value, int min, int max, Constants.FieldType fieldType){
        try{
            Integer parseValue = null;
            String upper = value.toUpperCase();
            switch (fieldType) {
                case DAY_OF_WEEK:
                    if(DAY_NAMES.containsKey(upper)){
                        parseValue = DAY_NAMES.get(upper);
                        break;
                    }
                    break;
                case MONTH:
                    if(MONTH_NAMES.containsKey(upper)){
                        parseValue = MONTH_NAMES.get(upper);
                        break;
                    }
            }
            if(parseValue == null){
                parseValue = Integer.parseInt(value);
            }
            if(parseValue < min || parseValue > max){
                throw new CronParseException("Value out of range [" + min + "," + max + "]: " + value);
            }
            return parseValue;
        } catch (NumberFormatException e){
            throw new CronParseException("Invalid value: " + value + " in Field: " + fieldType, e);
        }
    }

    public static boolean isValidYear(String year){
        return year.matches("^(\\*|\\d{4}(-\\d{4})?)(/\\d+)?(,(\\d{4}(-\\d{4})?)(/\\d+)?)*$");
    }
}
