package pl.aczek.exchange.account.exception;

public class InsufficientFundsException extends
    RuntimeException {

  public InsufficientFundsException(String message) {
    super(message);
  }
}
