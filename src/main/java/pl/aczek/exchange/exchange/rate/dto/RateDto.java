package pl.aczek.exchange.exchange.rate.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateDto {

  private String no;
  private String effectiveDate;
  private BigDecimal bid;
  private BigDecimal ask;

}
