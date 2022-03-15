package banckAccount;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Transaction {

    private final String operation;
    private final Date date;
    private final BigDecimal amount;

    private Transaction(String operation, Date date, BigDecimal amount) {
        this.operation = operation;
        this.date = date;
        this.amount = amount;
    }

    public static Transaction addTransaction(String operation,Date date, BigDecimal amount) {
        return new Transaction(operation, date, amount);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return operation.equals(that.operation) && date.equals(that.date) && amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, date, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "operation='" + operation + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount.setScale(2) +
                '}';
    }
}
