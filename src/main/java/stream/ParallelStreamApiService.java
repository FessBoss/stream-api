package stream;

import java.util.stream.Stream;

/**
 * Этот клас является информационным! Т.е. здесь, в основном, подсказки
 */
public class ParallelStreamApiService {

    /**
     * Для организации параллельного выполнения необходимо вызвать метод parallel
     * Отметим, что на практике вызов метода parallel для последовательного потока не
     * означает какого-либо конкретного преобразования самого потока.
     * «Под капотом» происходит установка булева флага, который указывает, что все следующие за parallel
     * операции необходимо выполнять параллельно.
     *
     * Аналогично можно преобразовать параллельный поток данных в последовательный, вызвав для него метод sequential
     */
    public long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }
}
