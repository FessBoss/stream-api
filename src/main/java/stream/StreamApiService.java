package stream;

import domain.Dish;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Этот клас является информационным! Т.е. здесь, в основном, подсказки
 */
public class StreamApiService {

    public enum CaloricLevel { DIET, NORMAL, FAT }

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

    /**
     * Потоки данных можно создавать на основе явно указанных значений с помощью статического метода Stream.of
     * Для создания пустого потока используется метод Stream.empty()
     */
    public void wordsToUpperCase() {
        Stream.of("Modern ", "Java ", "In ", "Action")
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    /**
     * Создание потока данных из объекта, допускающего неопределенное значение
     * При работе с потоками данных можно столкнуться с ситуацией, когда извлеченный объект (возможно, с неопределенным
     * значением) нужно преобразовать в поток данных (или пустой поток в случае объекта с неопределенным значением)
     */
    public void getProperty() {
        Stream<String> values = Stream.of("config", "home", "user")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
    }

    /**
     * Создание потоков данных из массивов
     *
     * Поток данных можно создать на основе массива, с помощью статического метода Arrays.stream,
     * принимающего в качестве параметра массив
     */
    public int sum() {
        int[] numbers = new int[]{1, 2, 3, 4, 5};
        return Arrays.stream(numbers).sum();
    }

    /**
     * Создание потоков данных из файлов
     *
     * API NIO (non-blocking I/O, неблокирующего ввода/вывода) языка Java, используемый для таких операций ввода/вывода,
     * как обработка файлов, был расширен для того, чтобы использовать возможности Stream API.
     */
    public void readFile() {
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
            //обработка файла
        }
    }

    /**
     * Операция iterate является принципиально последовательной для бесконечного потока данных,
     * поскольку результат зависит от предыдущего значения.
     */
    public void iterate() {
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
    }

    /**
     * Подобно методу iterate , метод generate дает возможность генерировать бесконечный поток значений,
     * вычисляемых по требованию. Но метод generate не применяет функцию последовательно к каждому сгенерированному значению.
     */
    public void generate() {
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }


    /**
     * количество блюд в меню с помощью коллектора, возвращаемого фабричным методом counting
     */
    public long howManyDishes(List<Dish> menu) {
        return menu.stream().collect(Collectors.counting());
    }

    /**
     * Для вычисления максимального или минимального значения в потоке данных можно использовать
     * два коллектора — Collectors.maxBy и Collectors.minBy.
     * Они принимают в качестве аргумента объект Comparator для сравнения элементов потока данных.
     */

    public Optional<Dish> mostCalorieDish(List<Dish> menu) {
        return menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
    }

    /**
     * В классе Collectors имеется специальный фабричный метод для суммирования:
     * Collectors.summingInt. Он принимает на входе функцию, отображающую объект
     * в суммируемое значение типа int, и возвращает коллектор, который при передаче
     * обычному методу collect вычисляет нужные сводные показатели
     */

    public int totalCalories(List<Dish> menu) {
        return menu.stream().collect(Collectors.summingInt(Dish::getCalories));
    }

    /**
     * Но вычисление сводных показателей — это отнюдь не только суммирование.
     * Метод Collectors.averagingInt вместе с его аналогами averagingLong и averagingDouble
     * позволяет вычислить среднее значение набора числовых значений
     */

    public double avgCalories(List<Dish> menu) {
        return menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
    }

    /**
     * Довольно часто, однако, бывает нужно извлечь два таких результата или
     * более, причем за одну операцию. В этом случае можно воспользоваться коллектором,
     * возвращаемым фабричным методом summarizingInt.
     * Вся информация собирается этим коллектором в объект класса IntSummaryStatistics,
     * из которого удобно получать результаты с помощью его методов-геттеров.
     */
    public IntSummaryStatistics menuStatistics(List<Dish> menu) {
        return menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
    }

    /**
     * Возвращаемый фабричным методом joining коллектор выполняет конкатенацию
     * всех строк, получаемых в результате вызовов метода toString для каждого из объектов потока данных
     */
    public String shortMenu(List<Dish> menu) {
//        return menu.stream().map(Dish::getName).collect(Collectors.joining());
        return menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
    }

    /**
     * вычислить суммарное количество калорий в меню можно с помощью коллектора, созданного на основе метода reducing
     */
    public int reducingTotalCalories(List<Dish> menu) {
        return menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
    }

    /**
     * Аналогичным образом можно найти самое калорийное блюдо с помощью одноаргументной версии метода reducing
     */
    public Optional<Dish> reducingMostCalorieDish(List<Dish> menu) {
        return menu.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
    }

    /**
     * Группировка с помощью Collectors.groupingBy
     */
    public Map<Dish.Type, List<Dish>> dishesByType(List<Dish> menu) {
        return menu.stream().collect(Collectors.groupingBy(Dish::getType));
    }

    /**
     * Но ссылка на метод не всегда подходит в качестве функции классификации,
     * ведь иногда простого извлечения свойств объекта для классификации недостаточно
     */
    public Map<CaloricLevel, List<Dish>> dishesByCaloricLevel(List<Dish> menu) {
        return menu.stream().collect(Collectors.groupingBy(dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }));
    }

    /**
     * В классе Collectors есть перегруженный фабричный метод groupingBy, принимающий второй аргумент типа
     * Collector наряду с обычной функцией классификации. Таким образом, появляется
     * возможность перенести предикат фильтрации внутрь этого второго коллектора
     */
    public Map<Dish.Type, List<Dish>> caloricDishesByType(List<Dish> menu) {
        return menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.filtering(dish -> dish.getCalories() > 500, Collectors.toList())));
    }

    /**
     * Для каждого объекта Dish мы получаем List пометок.
     * Поэтому аналогично изложенному в предыдущей главе нам нужно выполнить flatMap для схлопывания
     * полученного двухуровневого списка в одноуровневый
     */
    public Map<Dish.Type, Set<String>> dishNamesByType(List<Dish> menu) {
        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork", Arrays.asList("greasy", "salty"));
        dishTags.put("beef", Arrays.asList("salty", "roasted"));
        dishTags.put("chicken", Arrays.asList("fried", "crisp"));
        dishTags.put("french fries", Arrays.asList("greasy", "fried"));
        dishTags.put("rice", Arrays.asList("light", "natural"));
        dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
        dishTags.put("pizza", Arrays.asList("tasty", "salty"));
        dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
        dishTags.put("salmon", Arrays.asList("delicious", "fresh"));

        return menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.flatMapping(dish -> dishTags.get(dish.getName()).stream(),
                        Collectors.toSet())));
    }
}
