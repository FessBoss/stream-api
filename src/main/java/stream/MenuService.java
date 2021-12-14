package stream;

import domain.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class MenuService {

    public List<String> findThreeHignCaloricDishName(List<Dish> menu) {
        return menu.stream()                              //создаем поток данных из объекта menu (источник данных)
                .filter(dish -> dish.getCalories() > 300) //создаем конвейер операций: сначала отфильтровываем
                .map(Dish::getName)                       //получаем названия блюд
                .limit(3)                                 //выбираем только первые три
                .collect(Collectors.toList());            //сохраняем результаты в другом объекте List (запускает обработку)
    }
}
