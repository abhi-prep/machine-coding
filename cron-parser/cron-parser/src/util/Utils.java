package util;

import exception.CronParseException;

public class Utils {
    public static int parseValue(String value, int min, int max){
        try{
            int parseValue = Integer.parseInt(value);
            if(parseValue < min || parseValue > max){
                throw new CronParseException("Value out of range [" + min + "," + max + "]: " + value);
            }
            return parseValue;
        } catch (NumberFormatException e){
            throw new CronParseException("Invalid numeric value: " + value, e);
        }
    }
}
