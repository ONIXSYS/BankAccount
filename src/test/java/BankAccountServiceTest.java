import banckAccount.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountServiceTest {

    @Test
    @DisplayName("In order to save money As a bank client, i want to make a deposit in my account")
    void should_deposit_in_my_account() {

        //Given
        var account = BankAccount.withBalance(BigDecimal.ZERO);
        var amount = BigDecimal.valueOf(100.00);
        var initialBalance = account.getBalance();

        //When
        account.deposit(amount, "2021-12-01");

        //Then
        assertEquals(account.getBalance(), initialBalance.add(amount));
    }

    @Test
    @DisplayName("In order to save money As a bank client, i can't make a negative deposit in my account")
    void should_not_deposit_in_my_account_with_negative_amount() {

        //Given
        var account = BankAccount.withBalance(BigDecimal.ZERO);
        var amount = BigDecimal.valueOf(-100.00);
        var initialBalance = account.getBalance();

        //When + Then
        assertThrows(NegativeAmountDepositException.class, () -> account.deposit(amount, "2021-12-01"));
        assertEquals(account.getBalance(), initialBalance);
    }


    @Test
    @DisplayName("In order to retrieve some or all of my savings As a bank client I want to make a withdrawal from my account")
    void should_withdraw_from_my_account() {

        //Given
        var account = BankAccount.withBalance(BigDecimal.valueOf(200.00));
        var amount = BigDecimal.valueOf(100.00);
        var initialBalance = account.getBalance();

        //When
        account.withdraw(amount, "2021-12-01");

        //Then
        assertEquals(account.getBalance(), initialBalance.subtract(amount));
    }

    @Test
    @DisplayName("In order to retrieve some or all of my savings As a bank client I can't make a withdrawal from my account if balance is zero")
    void should_not_withdraw_from_my_account() {

        //Given
        var account = BankAccount.withBalance(BigDecimal.ZERO);
        var amount = BigDecimal.valueOf(100.00);
        var initialBalance = account.getBalance();

        //When + Then
        assertThrows(InsufficientFundException.class, () -> account.withdraw(amount, "2021-12-01"));
        assertEquals(account.getBalance(), initialBalance);

    }


    @Test
    @DisplayName("In order to check my operations, As a bank client, I want to see the history (operation, date, amount, balance) of my operations")
    void should_store_bank_account_operations() {

        //Given
        var account = BankAccount.withBalance(BigDecimal.valueOf(500.00));
        var expectedOperations = new Statement(List.of(
                Transaction.addTransaction("withdraw", Date.valueOf("2022-02-01"), BigDecimal.valueOf(100.00)),
                Transaction.addTransaction("deposit", Date.valueOf("2022-02-05"), BigDecimal.valueOf(50.00)),
                Transaction.addTransaction("withdraw", Date.valueOf("2022-02-06"), BigDecimal.valueOf(200.00))
        ));

        //When
        account.withdraw(BigDecimal.valueOf(100.00), "2022-02-01");
        account.deposit(BigDecimal.valueOf(50.00), "2022-02-05");
        account.withdraw(BigDecimal.valueOf(200.00), "2022-02-06");

        //Then
        assertEquals(account.getStatement(), expectedOperations);

    }

    @Test
    @DisplayName("In order to check my operations, As a bank client, I want to print the history (operation, date, amount, balance) of my operations")
    void should_print_bank_account_operations() {
        //Given
        var statementHistory = new Statement(List.of(
                Transaction.addTransaction("withdraw", Date.valueOf("2022-02-01"), BigDecimal.valueOf(200.00))
        ));
        var account = BankAccount.withBalanceAndStatement(BigDecimal.valueOf(500.00), statementHistory);
        account.withdraw(BigDecimal.valueOf(100.00), "2022-02-01");
        account.deposit(BigDecimal.valueOf(50.00), "2022-02-05");

        var expectedPrintResult = """
                Transaction{operation='withdraw', date='2022-02-01', amount=200.00}
                Transaction{operation='withdraw', date='2022-02-01', amount=100.00}
                Transaction{operation='deposit', date='2022-02-05', amount=50.00}
                Balance{balance=450.00}""";

        //When
        var printResult = account.printStatement();

        //Then
        assertEquals(printResult, expectedPrintResult);
    }
}
