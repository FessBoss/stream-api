package stream;

import domain.Dish;
import domain.Menu;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamApiServiceTest {

    private static StreamApiService streamApiService;
    private static List<Dish> menu;

    @BeforeAll
    public static void setup() {
        streamApiService = new StreamApiService();
        menu = Menu.getMenu();
    }

    @Test
    public void findThreeHignCaloricDishName() {
        List<String> threeHignCaloricDishName = streamApiService.findThreeHignCaloricDishName(menu);
        List<String> exceptedResult = List.of("pork", "beef", "chicken");
        for (int i = 0; i < threeHignCaloricDishName.size(); i++) {
            assertEquals(exceptedResult.get(i), threeHignCaloricDishName.get(i));
        }
    }

    @Test
    public void streamThrowException() {
        assertThrows(IllegalStateException.class, () -> {
            List<String> title = Arrays.asList("a", "b", "c");
            Stream<String> s = title.stream();
            s.forEach(System.out::print);
            s.forEach(System.out::print); //в стриме можно пройтись по объектам только один раз!
        });
    }

    @Test
    public void testExternalIterationAndIterator() {
        List<String> namesUsesForEach = streamApiService.findNamesUsesForEach(menu);
        List<String> namesUsesIterator = streamApiService.findNamesUsesIterator(menu);

        for (int i = 0; i < namesUsesForEach.size(); i++) {
            assertEquals(namesUsesForEach.get(i), namesUsesIterator.get(i));
        }
    }

    @Test
    public void testExternalIterationAndInternalIteration() {
        List<String> namesUsesForEach = streamApiService.findNamesUsesForEach(menu);
        List<String> namesUsesStream = streamApiService.findNamesUsesStream(menu);

        for (int i = 0; i < namesUsesForEach.size(); i++) {
            assertEquals(namesUsesForEach.get(i), namesUsesStream.get(i));
        }
    }

    @Test
    public void testTakeWhileFilteredMenu() {
        List<Integer> dishes = streamApiService.takeWhileFilteredMenu(menu);

        assertEquals(120, dishes.get(0));
        assertEquals(300, dishes.get(1));
    }

    @Test
    public void testDropWhileFilteredMenu() {
        List<Integer> dishes = streamApiService.dropWhileFilteredMenu(menu);

        assertEquals(350, dishes.get(0));
        assertEquals(400, dishes.get(1));
        assertEquals(450, dishes.get(2));
        assertEquals(530, dishes.get(3));
        assertEquals(550, dishes.get(4));
    }

    @Test
    public void testFindPairs() {
        List<Integer> numbers1 = List.of(1, 2, 3);
        List<Integer> numbers2 = List.of(3, 4);
        List<int[]> exceptedResult = List.of(
                new int[] {2, 4},
                new int[] {3, 3}
        );

        List<int[]> result = streamApiService.findPairs(numbers1, numbers2);
        for (int i = 0; i < exceptedResult.size(); i++) {
            int[] pair1 = exceptedResult.get(i);
            int[] pair2 = result.get(i);
            for (int j = 0; j < pair1.length; j++) {
                assertEquals(pair1[j], pair2[j]);
            }
        }
    }
}