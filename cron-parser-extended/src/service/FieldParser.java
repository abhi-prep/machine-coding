package service;

import util.Constants;

import java.util.List;

public interface FieldParser {
    List<Integer> parse(String fieldValue, int min, int max, Constants.FieldType fieldType);
}
