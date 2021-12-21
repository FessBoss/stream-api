package stream;

import domain.Trader;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TraderService {

    //Вывести список неповторяющихся городов, в которых работают трейдеры
    public List<String> findUniqueTraderCities(List<Trader> traders) {
        return traders.stream()
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());
    }

    //Найти всех трейдеров из Кембриджа и отсортировать их по именам
    public List<Trader> findTradersFromCambridgeSortedByName(List<Trader> traders) {
        return traders.stream()
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
    }
}
