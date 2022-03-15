package banckAccount;

import java.math.BigDecimal;

import static java.sql.Date.valueOf;

public class BankAccount implements BankAccountApi {

    private BigDecimal balance;
    private Statement statement;

    private BankAccount(BigDecimal initialBalance) {
        this.statement = new Statement();
        this.balance = initialBalance;
    }

    private BankAccount(BigDecimal initialBalance, Statement statement) {
        this.statement = new Statement(statement.getTransactions());
        this.balance = initialBalance;
    }

    public static BankAccount withBalance(BigDecimal initialBalance) {
        return new BankAccount(initialBalance);
    }

    public static BankAccount withBalanceAndStatement(BigDecimal initialBalance, Statement statement) {
        return new BankAccount(initialBalance, statement);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void deposit(BigDecimal amount, String transactionDate) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountDepositException();
        }
        balance = balance.add(amount);
        statement.deposit(valueOf(transactionDate), amount);
    }

    @Override
    public void withdraw(BigDecimal amount, String transactionDate) {
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundException();
        }
        balance = balance.subtract(amount);
        statement.withdraw(valueOf(transactionDate), amount);
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public String printStatement() {
        return statement.printStatement();
    }
}
