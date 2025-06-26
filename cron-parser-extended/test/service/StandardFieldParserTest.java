package service;

import exception.CronParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import service.impl.StandardFieldParser;
import util.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StandardFieldParserTest {
    private FieldParser fieldParser;

    @BeforeEach
    void setUp(){
        fieldParser = new StandardFieldParser();
    }

    @Test
    void testParseAsterisk(){
        List<Integer> expected = IntStream.rangeClosed(0, 59).boxed().toList();
        List<Integer> result = fieldParser.parse("*", 0, 59, Constants.FieldType.MINUTE);
        assertEquals(expected, result);
    }

    @Test
    void testParseAsteriskWithStep(){
        List<Integer> expected = Arrays.asList(0, 15, 30, 45);
        List<Integer> result = fieldParser.parse("*/15", 0, 59, Constants.FieldType.MINUTE);
        assertEquals(expected, result);
    }

    @Test
    void testParseList(){
        List<Integer> expected = Arrays.asList(1, 5, 10);
        List<Integer> result = fieldParser.parse("1,5,10", 0, 59, Constants.FieldType.MINUTE);
        assertEquals(expected, result);
    }

    @Test
    void testParseRange(){
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> result = fieldParser.parse("1-5", 0, 59, Constants.FieldType.MINUTE);
        assertEquals(expected, result);
    }

    @Test
    void testParseRangeWithStep(){
        List<Integer> expected = Arrays.asList(1, 3, 5);
        List<Integer> result = fieldParser.parse("1-5/2", 0, 59, Constants.FieldType.MINUTE);
        assertEquals(expected, result);
    }

    @Test
    void testParseSingleValue(){
        List<Integer> expected = List.of(5);
        List<Integer> result = fieldParser.parse("5", 0, 59, Constants.FieldType.MINUTE);
        assertEquals(expected, result);
    }

    @Test
    void testParseValueWithStep(){
        List<Integer> expected = Arrays.asList(5, 10, 15, 20);
        List<Integer> result = fieldParser.parse("5/5", 0, 20, Constants.FieldType.MINUTE);
        assertEquals(expected, result);
    }

    @Test
    void testParseOutOfRangeThrowsException(){
        assertThrows(CronParseException.class, () -> fieldParser.parse("60", 0, 59, Constants.FieldType.MINUTE));
    }

    @Test
    void testParseInvalidFormatThrowsException(){
        assertThrows(CronParseException.class, () -> fieldParser.parse("a", 0, 59, Constants.FieldType.MINUTE));
    }

    @Test
    void testParseNegativeStepThrowsException(){
        assertThrows(CronParseException.class, () -> fieldParser.parse("*/-1", 0, 59, Constants.FieldType.MINUTE));
    }

    @Test
    void testParseEmptyFieldThrowsException(){
        assertThrows(CronParseException.class, () -> fieldParser.parse("", 0, 59, Constants.FieldType.MINUTE));
    }

    @Test
    void testParseNullFieldThrowsException(){
        assertThrows(CronParseException.class, () -> fieldParser.parse(null, 0, 59, Constants.FieldType.MINUTE));
    }

    @ParameterizedTest
    @MethodSource("provideComplexExpressions")
    void testParseComplexExpressions(String expressions, int min, int max, Constants.FieldType fieldType, List<Integer> expected){
        List<Integer> result = fieldParser.parse(expressions, min, max, fieldType);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideComplexExpressions(){
        return Stream.of(
            Arguments.of("1,2,3,4,5", 0, 59, Constants.FieldType.MINUTE, Arrays.asList(1,2,3,4,5)),
            Arguments.of("1-5,10,15-17", 0, 59, Constants.FieldType.MINUTE, Arrays.asList(1,2,3,4,5,10,15,16,17)),
            Arguments.of("1-10/2", 0, 59, Constants.FieldType.MINUTE, Arrays.asList(1,3,5,7,9)),
            Arguments.of("*/10,5,6,7", 0, 59, Constants.FieldType.MINUTE, Arrays.asList(0,5,6,7,10,20,30,40,50))
        );
    }
}
