package banckAccount;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Statement {

    public static final String DEPOSIT = "deposit";
    public static final String WITHDRAW = "withdraw";

    private List<Transaction> transactions;

    public Statement() {
        transactions = new ArrayList<>();
    }

    public Statement(List<Transaction> transactions) {
        this.transactions = new ArrayList<>(transactions);
    }

    public void deposit(Date date, BigDecimal amount) {
        transactions.add(Transaction.addTransaction(DEPOSIT, date, amount));
    }

    public void withdraw(Date date, BigDecimal amount) {
        transactions.add(Transaction.addTransaction(WITHDRAW, date, amount));
    }

    public String printStatement() {
        return transactions.stream()
                .map(Transaction::toString)
                .collect(Collectors.joining("\n"));
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statement statement = (Statement) o;
        return Objects.equals(transactions, statement.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }
}
