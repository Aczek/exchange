package pl.aczek.exchange.account.exception;

public class CurrencyAccountNotFoundException extends
    RuntimeException {

  public CurrencyAccountNotFoundException(String message) {
    super(message);
  }
}
