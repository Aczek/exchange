package pl.aczek.exchange.account.dto;

import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.aczek.exchange.account.exception.InvalidCurrencySymbolException;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRequest {

  Currency from;
  Currency to;
  @Digits(integer = 255, fraction = 2)
  BigDecimal amount;

  public static ExchangeRequest from(Currency from, Currency to, BigDecimal amount) {
    return new ExchangeRequest(from, to, amount);
  }

  public static ExchangeRequest from(ExchangeRequestDto exchangeRequestDto) {
    try {
      return new ExchangeRequest(Currency.getInstance(exchangeRequestDto.getFrom().toUpperCase()),
          Currency.getInstance(exchangeRequestDto.getTo().toUpperCase()),
          exchangeRequestDto.getAmount());
    } catch (IllegalArgumentException ex) {
      throw new InvalidCurrencySymbolException("Invalid currency symbol");
    }

  }

}
