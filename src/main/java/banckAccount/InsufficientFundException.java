package banckAccount;

public class InsufficientFundException extends RuntimeException{
    public InsufficientFundException() {
        super("Could not withdraw, insufficient fund in your bank account");
    }
}
