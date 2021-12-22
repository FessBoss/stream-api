package stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InfiniteStreamApi {

    //сгенерировать первые 20 элементов последовательности пар чисел Фибоначчи с помощью метода iterate
    public List<int[]> findFibonacciNumbers(int limit) {
        return Stream.iterate(new int[] {0, 1}, n -> new int[]{n[1], n[0] + n[1]})
                .limit(limit)
                .collect(Collectors.toList());
    }
}
