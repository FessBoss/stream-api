package stream;

import domain.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Этот клас является информационным! Т.е. здесь, в основном, подсказки
 */
public class StreamApiService {

    public List<String> findThreeHignCaloricDishName(List<Dish> menu) {
        return menu.stream()                              //создаем поток данных из объекта menu (источник данных)
                .filter(dish -> dish.getCalories() > 300) //создаем конвейер операций: сначала отфильтровываем
                .map(Dish::getName)                       //получаем названия блюд
                .limit(3)                                 //выбираем только первые три
                .collect(Collectors.toList());            //сохраняем результаты в другом объекте List (запускает обработку)
    }

    /**
     * Коллекции имеют внешнюю итерацию
     */
    public List<String> findNamesUsesForEach(List<Dish> menu) {
        List<String> names = new ArrayList<>();
        for (Dish dish : menu) {
            names.add(dish.getName());
        }
        return names;
    }

    /**
     * Как работает for each на самом деле
     */
    public List<String> findNamesUsesIterator(List<Dish> menu) {
        List<String> names = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            names.add(dish.getName());
        }
        return names;
    }

    /**
     * потоки имеют внутреннюю итерацию
     */
    public List<String> findNamesUsesStream(List<Dish> menu) {
        return menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
    }

    /**
     * Фильтрация уникальных значений distinct (в соответствии с hashcode и equals)
     */
    public List<Integer> findEvenUniqNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 == 0)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Недостаток операции filter состоит в том, что она требует прохода по всему потоку данных
     * с применением предиката ко всем элемента.
     *
     * Если коллекция уже упорядочена, то можно воспользоваться срезом takeWhile
     */
    public List<Integer> takeWhileFilteredMenu(List<Dish> menu) {
        return menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories))
                .takeWhile(dish -> dish.getCalories() < 320)
                .map(Dish::getCalories)
                .collect(Collectors.toList());
    }

    /**
     * Получить все остальные элементы в отсортированной коллекции
     */
    public List<Integer> dropWhileFilteredMenu(List<Dish> menu) {
        return menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories))
                .dropWhile(dish -> dish.getCalories() < 320)
                .map(Dish::getCalories)
                .collect(Collectors.toList());
    }

    /**
     * Усечение потока данных limit(n)
     * Возвращает еще один поток, не превышающий заданную длинну
     */
    public List<Dish> findFreeHighCaloricDishName(List<Dish> menu) {
        return menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Поток данных поддерживает метод skip(n), возвращающий поток
     * с отброшенными первыми n элементов источника
     */
    public List<Dish> skipDishes(List<Dish> menu) {
        return menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .collect(Collectors.toList());
    }

    //найти квадраты передаваемых чисел
    public List<Integer> findSquares(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> number * number)
                .collect(Collectors.toList());
    }

    //По двум заданным спискам вернуть всех их попарные сочетания, которые делятся на 3 без остатка
    public List<int[]> findPairs(List<Integer> numbers1, List<Integer> numbers2) {
        return numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[] {i, j}))
                .collect(Collectors.toList());
    }

    //Найти уникальные символы
    public List<String> findUniqueChars(List<String> words) {
        return words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * anyMatch служит для ответа на вопрос: "удовлетворяет ли заданному предикату хотя бы один элемент из потока данных?"
     * является завершающей операцией
     */
    public boolean isMenuVegetarian(List<Dish> menu) {
        return menu.stream()
                .anyMatch(Dish::isVegetarian);
    }

    /**
     * allMatch работает аналогично anyMatch, но проверяет, удовлетворяют ли заданному предикату все элементы потока данных
     *
     * противоположностью allMatch является метод noneMatch
     * он проверяет, точно ли ни один элемент из списка не соответствует заданному условию
     */
    public boolean isHealthy(List<Dish> menu) {
        return menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000);
    }

    /**
     * reduce служит последовательного вычисления
     * например, для последовательного сложения или умножения
     * или для вычисления максимального или минимального элемента
     */
    public int findSum(List<Integer> numbers) {
        return numbers.stream()
                .reduce(0, (a, b) -> a + b);
    }

    public int findMax(List<Integer> numbers) {
        return numbers.stream()
                .reduce(0, Integer::max);
    }

    /**
     * Иногда проблема состоит в скрытых затратах на упаковку.
     * Каждый объект Integer необходимо неявно распаковывать до простого типа данных, прежде чем прибавить к сумме.
     *
     * Для таких случаев есть версии потоков для примитивных типов данных.
     * Кроме того, в них есть методы для обратного их преобразования в поток объектов (при необходимости)
     */
    public int findCaloriesSum(List<Dish> menu) {
        return menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
    }

    /**
     * Для работы с числовыми диапазонами в интерфейсах IntStream и LongStream используются методы range и rangeCLosed
     * rangeClose включает границы диапазона, а range нет
     */
    public long evenNumbers(int range, int rangeClosed) {
        return IntStream.rangeClosed(range, rangeClosed)
                .filter(n -> n % 2 == 0)
                .count();
    }
}
