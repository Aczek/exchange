package pl.aczek.exchange.exchange.rate.exception;

public class ExchangeRateNotFoundException extends RuntimeException {

  public ExchangeRateNotFoundException(String message) {
    super(message);
  }
}
