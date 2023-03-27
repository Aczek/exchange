package pl.aczek.exchange.account.exception;

public class InvalidOperationException extends
    RuntimeException {

  public InvalidOperationException(String message) {
    super(message);
  }
}
