package stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InfiniteStreamApiTest {

    private static InfiniteStreamApi infiniteStreamApi;

    @BeforeAll
    public static void setUp() {
        infiniteStreamApi = new InfiniteStreamApi();
    }

    @Test
    void findFibonacciNumbers_test() {
        List<int[]> fibonacciNumbers = infiniteStreamApi.findFibonacciNumbers(20);
        fibonacciNumbers.forEach(n -> System.out.printf("(%d, %d)%n", n[0], n[1]));
    }
}