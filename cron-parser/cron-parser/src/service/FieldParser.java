package service;

import java.util.List;

public interface FieldParser {
    List<Integer> parse(String fieldValue, int min, int max);
}
