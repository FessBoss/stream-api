package stream;

import domain.Dish;
import domain.Menu;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {

    private static MenuService menuService;
    private static List<Dish> menu;

    @BeforeAll
    public static void setup() {
        menuService = new MenuService();
        menu = Menu.getMenu();
    }

    @Test
    void findThreeHignCaloricDishName() {
        List<String> threeHignCaloricDishName = menuService.findThreeHignCaloricDishName(menu);
        List<String> exceptedResult = List.of("pork", "beef", "chicken");
        for (int i = 0; i < threeHignCaloricDishName.size(); i++) {
            assertEquals(exceptedResult.get(i), threeHignCaloricDishName.get(i));
        }
    }
}