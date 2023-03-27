package pl.aczek.exchange.account.domain;

import java.math.BigDecimal;
import java.util.Currency;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CurrencyAccount {

  Currency currency;
  BigDecimal balance;

  public static CurrencyAccount from(Currency currency, BigDecimal balance) {
    return CurrencyAccount.builder()
        .currency(currency)
        .balance(balance)
        .build();

  }

  public CurrencyAccount add(BigDecimal add) {
    return CurrencyAccount.builder()
        .currency(this.currency)
        .balance(add.add(this.balance))
        .build();
  }

  public CurrencyAccount subtract(BigDecimal subtract) {
    return CurrencyAccount.builder()
        .currency(this.currency)
        .balance(this.balance.subtract(subtract))
        .build();
  }

}
