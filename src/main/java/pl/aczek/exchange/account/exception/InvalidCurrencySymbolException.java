package pl.aczek.exchange.account.exception;

public class InvalidCurrencySymbolException extends RuntimeException {

  public InvalidCurrencySymbolException(String message) {
    super(message);
  }
}
