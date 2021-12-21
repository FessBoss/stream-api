package stream;

import domain.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {

    //Найти все транзакции за 2011 год и отсортировать их по сумме(от меньшей к большей)
    public List<Transaction> findSortedTransactionBy2011Year(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .collect(Collectors.toList());
    }

    //Вывести суммы всех транзакций трейдеров из Кембриджа
    public List<Integer> findTraderFromCambridgeTransactionValue(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .collect(Collectors.toList());
    }

    //Какова максимальная сумма среди всех транзакций?
    public int findMaxTransactionValue(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::max);
    }
}
