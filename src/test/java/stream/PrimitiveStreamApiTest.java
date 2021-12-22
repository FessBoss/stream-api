package stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrimitiveStreamApiTest {

    private static PrimitiveStreamApi primitiveStreamApi;

    @BeforeAll
    public static void setUp() {
        primitiveStreamApi = new PrimitiveStreamApi();
    }

    @Test
    void findPythagoreanTriples_test() {
        List<int[]> exceptedResult = List.of(
                new int[]{3, 4, 5},
                new int[]{5, 12, 13},
                new int[]{6, 8, 10},
                new int[]{7, 24, 25},
                new int[]{8, 15, 17}
        );

        List<int[]> pythagoreanTriples = primitiveStreamApi.findPythagoreanTriples(1, 100, 5);
        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }
}