package pl.aczek.exchange.rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pl.aczek.exchange.account.dto.ExchangeRequest;

@Slf4j
@Builder
@Getter
@ToString
public class ExchangeRate {

  private static final int SCALE = 2;
  Currency from;
  Currency to;
  BigDecimal conversionRate;

  public BigDecimal getAmountAfterConversion(ExchangeRequest exchangeRequest) {
    validateCurrencies(exchangeRequest);
    return calculateAmountAfterConversion(exchangeRequest);

  }

  private BigDecimal calculateAmountAfterConversion(ExchangeRequest exchangeRequest) {
    return exchangeRequest.getAmount().multiply(this.conversionRate)
        .setScale(SCALE, RoundingMode.HALF_UP);
  }

  private void validateCurrencies(ExchangeRequest exchangeRequest) {
    if (!exchangeRequest.getFrom().equals(this.from)
        || !exchangeRequest.getTo().equals(this.to)) {
      throw new IllegalArgumentException("invalid currencies for conversion!");
    }
  }
}
