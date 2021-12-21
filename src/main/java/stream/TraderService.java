package stream;

import domain.Trader;

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
}
