package pl.aczek.exchange.account.data;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CurrencyAccountEmbed {

  private Currency currency;
  private BigDecimal balance;

}
