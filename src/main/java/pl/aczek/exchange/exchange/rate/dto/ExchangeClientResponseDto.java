package pl.aczek.exchange.exchange.rate.dto;


import java.util.Currency;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeClientResponseDto {

  private String table;
  private String currency;
  private Currency code;
  private List<RateDto> rates;


}
