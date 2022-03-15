package banckAccount;

import java.math.BigDecimal;

public interface BankAccountApi {

    void deposit(BigDecimal amount, String transactionDate);

    void withdraw(BigDecimal amount, String transactionDate);

    Statement getStatement();

    String printStatement();
}
