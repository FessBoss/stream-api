package stream;

import domain.Dish;
import domain.Menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
}
