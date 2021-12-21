package stream;

import domain.Trader;
import domain.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TraderServiceTest {
    private Trader raoul;
    private Trader mario;
    private Trader alan;
    private Trader brian;

    private List<Trader> traders;

    private static TraderService traderService;

    @BeforeEach
    public void setUp() {
        traders = List.of(
                raoul = new Trader("Raoul", "Cambridge"),
                mario = new Trader("Mario","Milan"),
                alan = new Trader("Alan","Cambridge"),
                brian = new Trader("Brian","Cambridge"));
    }

    @BeforeAll
    public static void setUpService() {
        traderService = new TraderService();
    }

    @Test
    void findUniqueTraderCities() {
        List<String> exceptedResult = List.of("Cambridge", "Milan");
        List<String> result = traderService.findUniqueTraderCities(traders);

        for (int i = 0; i < exceptedResult.size(); i++) {
            assertEquals(exceptedResult.get(i), result.get(i));
        }
    }
}