package pl.aczek.exchange.rate.exception;

public class ExchangeRateNotFoundException extends RuntimeException {

  public ExchangeRateNotFoundException(String message) {
    super(message);
  }
}
