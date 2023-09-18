package dev.projects.sspSoft.javaCore.ec4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EC4Tests {

    /**
     * Создать ArrayList и заполните его целочисленными значениями от 1 до 10 000 000 с шагом 10;
     */
    @Test
    public void firstTest() {
        List<Integer> sourceList = new ArrayList<>();

        for (int value = 1; value <= 10_000_000; value += 10) {
            sourceList.add(value);
        }

        Assertions.assertEquals(1_000_000, sourceList.size());
    }

    /**
     * Реализовать метод, принимающий на вход List, и возвращающий первый элемент этого списка;
     */
    @Test
    public void secondTest() {
        List<Integer> sourceList = IntStream.rangeClosed(10, 100).boxed().collect(Collectors.toList());
        Integer first = getFirst(sourceList);

        Assertions.assertEquals(10, first);
    }

    private Integer getFirst(List<Integer> list) {
        return list.get(0);
    }

    /**
     * Реализовать метод, аргументами которого являются коллекция и объект. Метод должен удалить из коллекции все объекты, меньшие чем указанный;
     */
    @Test
    public void thirdTest() {
        List<Integer> sourceList = IntStream.rangeClosed(1, 15).boxed().collect(Collectors.toList());
        List<Integer> thresholdedList = deleteIntegerWhichIsLessThen(sourceList, 10);

        Assertions.assertTrue(thresholdedList.stream().anyMatch((value) -> value > 10));
    }

    private List<Integer> deleteIntegerWhichIsLessThen(List<Integer> list, Integer threshold) {
        return list.stream().filter((value) -> value > threshold).collect(Collectors.toList());
    }

    /**
     * Реализовать метод, получающий на вход List объектов и возвращающий новый List, в котором все объекты разные.
     * Подготовить 2 реализации: с применением Stream API и без его использования;
     */
    @Test
    public void fourthTestByCycle() {
        List<Double> sourceList = List.of(1.0, 1.0, 1.0, 1.0);
        List<Double> modifiedList = getDifferentByCycle(sourceList);

        Assertions.assertNotEquals(1.0, modifiedList.get(0));
        Assertions.assertNotEquals(1.0, modifiedList.get(1));
        Assertions.assertNotEquals(1.0, modifiedList.get(2));
        Assertions.assertNotEquals(1.0, modifiedList.get(3));
    }

    private List<Double> getDifferentByCycle(List<Double> list) {
        Random rnd = new Random(System.currentTimeMillis());

        List<Double> newList = new ArrayList<>();

        for (Double value : list) {
            newList.add(rnd.nextDouble() * value);
        }

        return newList;
    }

    /**
     * Реализовать метод, получающий на вход List объектов и возвращающий новый List, в котором все объекты разные.
     * Подготовить 2 реализации: с применением Stream API и без его использования;
     */
    @Test
    public void fourthTestByStream() {
        List<Double> list = List.of(1.0, 1.0, 1.0, 1.0);
        List<Double> modifiedList = getDifferentByStream(list);

        Assertions.assertNotEquals(1.0, modifiedList.get(0));
        Assertions.assertNotEquals(1.0, modifiedList.get(1));
        Assertions.assertNotEquals(1.0, modifiedList.get(2));
        Assertions.assertNotEquals(1.0, modifiedList.get(3));
    }

    private List<Double> getDifferentByStream(List<Double> list) {
        Random rnd = new Random(System.currentTimeMillis());

        return list.stream().map((value) -> rnd.nextDouble() * value).collect(Collectors.toList());
    }

    /**
     * Реализовать метод, удаляющий из HashMap<String, String> все записи,
     * в которых сумма длин ключа и значения равна 10;
     */
    @Test
    public void fiveTestDeleteByKeyAndValueLength() {
        Map<String, String> sourceMap = new HashMap<>();

        sourceMap.put("0123", "0123");
        sourceMap.put("01234", "01234");
        sourceMap.put("012345", "012345");

        // Boolean function of one argument
        Predicate<Map.Entry<String, String>> filterPredicate = entry -> (entry.getKey().length() + entry.getValue().length()) != 10;

        Map<String, String> result = sourceMap
                .entrySet()
                .stream()
                .filter(filterPredicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Assertions.assertTrue(result.entrySet().stream().anyMatch(filterPredicate));
        Assertions.assertEquals(2L, result.entrySet().stream().count());
    }

    @Test
    @Benchmark
    public void createCollection() {
        List<Integer> list = IntStream.rangeClosed(0, 1024).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public void cloneCollection() {

    }

    /**
     * Имеется List, содержащий строки: List<String> list = List.of("A B C D A B", "B A C D B F", "A A B C E F");
     * Необходимо с помощью Stream API построить Map, в котором ключ будет являться строка с одной из букв,
     * а значением - кол-во повторений в строках этого списка;
     */
    @Test
    public void keyAndValueMapBuildTest() {
        List<String> list = List.of("A B C D A B", "B A C D B F", "A A B C E F");

        Map<String, Long> result = list.stream()
                .flatMap(string -> Arrays.stream(string.split(" ")))
                .collect(Collectors.groupingBy(String::valueOf, Collectors.counting()));

        Assertions.assertEquals(5, result.get("A"));
        Assertions.assertEquals(5, result.get("B"));
        Assertions.assertEquals(3, result.get("C"));
        Assertions.assertEquals(2, result.get("D"));
        Assertions.assertEquals(1, result.get("E"));
        Assertions.assertEquals(2, result.get("F"));
    }

    /**
     * Создать список, состоящий из 100 чисел от 1 до 10.
     * Решить задачу в одну строку: найти 3-ее наиболее встречающееся число в этом списке;
     */
    @Test
    public void numberListByFrequencyFirst() {
        Random rnd = new Random(System.currentTimeMillis());

        // итоговая длина равна 100
        Map<Integer, Long> frequencyMap = Stream
                .concat(Stream.of(1,1,1), rnd.ints(97, 2, 10).boxed())
                .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

        Map.Entry<Integer, Long> thirdEntry = frequencyMap.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .filter(entry -> entry.getValue() == 3L)
                .findFirst()
                .get();

        Assertions.assertEquals(3L, thirdEntry.getValue());
    }

    /**
     * Создать список, состоящий из 100 чисел от 1 до 10.
     * Решить задачу в одну строку: получить список уникальных чисел,
     * которые в исходном списке встречаются не менее 5 раз.
     */
    @Test
    public void numberListByFrequencySecond() {
        Random rnd = new Random(System.currentTimeMillis());

        // итоговая длина равна 100
        Map<Integer, Long> frequencyMap = Stream
                .concat(Stream.of(1,1,1,1,1), rnd.ints(95, 2, 10).boxed())
                .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

        Map<Integer, Long> fiveFrequencyEntries = frequencyMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 4L)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for(Long value: fiveFrequencyEntries.values()) {
            Assertions.assertTrue(value > 4L);
        }
    }
}
