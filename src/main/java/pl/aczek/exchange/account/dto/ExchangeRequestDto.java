package pl.aczek.exchange.account.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRequestDto {

  private String from;
  private String to;
  private BigDecimal amount;

}
