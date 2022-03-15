package banckAccount;

public class NegativeAmountDepositException extends RuntimeException {
    public NegativeAmountDepositException() {
        super("Negative amount deposite is not allowed");
    }
}
