package pl.aczek.exchange.account.dto;

import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

}
