package stream;

import domain.Trader;
import domain.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    private Trader raoul;
    private Trader mario;
    private Trader alan;
    private Trader brian;

    private List<Transaction> transactions;

    private static TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        raoul = new Trader("Raoul", "Cambridge");
        mario = new Trader("Mario","Milan");
        alan = new Trader("Alan","Cambridge");
        brian = new Trader("Brian","Cambridge");

        transactions = List.of(
                new Transaction(raoul, 2011, 400),
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    @BeforeAll
    public static void setUpService() {
        transactionService = new TransactionService();
    }

    @Test
    public void findSortedTransactionBy2011Year_test() {
        List<Transaction> result = transactionService.findSortedTransactionBy2011Year(transactions);
        List<Transaction> exceptedResult = List.of(transactions.get(1), transactions.get(0));

        for (int i = 0; i < exceptedResult.size(); i++) {
            assertEquals(exceptedResult.get(i), result.get(i));
        }
    }

    @Test
    public void findTraderFromCambridgeTransactionValue_test() {
        List<Integer> exceptedResult = List.of(400, 300, 1000, 950);
        List<Integer> result = transactionService.findTraderFromCambridgeTransactionValue(transactions);

        for (int i = 0; i < exceptedResult.size(); i++) {
            assertEquals(exceptedResult.get(i), result.get(i));
        }
    }
}