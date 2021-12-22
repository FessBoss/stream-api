package stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimitiveStreamApi {

    //создать поток пифагоровых троек
    public List<int[]> findPythagoreanTriples(int range, int rangeClosed, int limit) {
        return IntStream.rangeClosed(range, rangeClosed)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[] {a, b, (int) Math.sqrt(a * a + b * b)}))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
