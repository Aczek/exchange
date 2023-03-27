package pl.aczek.exchange.rate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pl.aczek.exchange.rate.dto.ExchangeClientResponseDto;
import pl.aczek.exchange.rate.exception.ExchangeRateNotFoundException;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ExchangeClient {

  private static final String BASE_URL = "http://api.nbp.pl/api/";
  private static final String CONVERSION_TABLE_TYPE = "C";

  @Retry(name = "nbpConversionService")
  @CircuitBreaker(name = "nbpConversionService")
  public ExchangeRate getExchangeRate(ExchangeRateRequest exchangeRateRequest) {
    if (Currency.getInstance("PLN").equals(exchangeRateRequest.getFrom())) {
      return getBuyRate(exchangeRateRequest);
    }
    if (Currency.getInstance("PLN").equals(exchangeRateRequest.getTo())) {
      return getSaleRate(exchangeRateRequest);
    }

    throw new ExchangeRateNotFoundException(
        "conversion rate not including sale/buy of PLN - all prices in NBP provider based on PLN");
  }

  private ExchangeRate getSaleRate(ExchangeRateRequest exchangeRateRequest) {
    Mono<ExchangeClientResponseDto> conversion = getConversionRates(
        exchangeRateRequest.getFrom());

    return conversion.blockOptional().map(e -> ExchangeRate.builder()
            .from(exchangeRateRequest.getFrom())
            .to(exchangeRateRequest.getTo())
            .conversionRate(getBid(e)).build())
        .orElseThrow(() -> new ExchangeRateNotFoundException(
            "no conversion rate table, check conversion api provider"));
  }

  private ExchangeRate getBuyRate(ExchangeRateRequest exchangeRateRequest) {
    Mono<ExchangeClientResponseDto> conversion = getConversionRates(
        exchangeRateRequest.getTo());
    return conversion.blockOptional().map(e -> ExchangeRate.builder()
            .from(exchangeRateRequest.getFrom())
            .to(exchangeRateRequest.getTo())
            .conversionRate(BigDecimal.ONE.divide(getAsk(e), 30, RoundingMode.HALF_UP))
            .build())
        .orElseThrow(() -> new ExchangeRateNotFoundException(
            "no conversion rate table, check conversion api provider"));
  }

  private static BigDecimal getBid(ExchangeClientResponseDto e) {
    return e.getRates().stream().findFirst().orElseThrow(
            () -> new ExchangeRateNotFoundException(
                "missing rate for currency in conversion, check conversion api provider"))
        .getBid();
  }

  private static BigDecimal getAsk(ExchangeClientResponseDto e) {
    return e.getRates().stream().findFirst().orElseThrow(
            () -> new ExchangeRateNotFoundException(
                "missing rate for currency in conversion, check conversion api provider"))
        .getAsk();
  }

  private Mono<ExchangeClientResponseDto> getConversionRates(Currency currency) {
    return WebClient.builder().baseUrl(BASE_URL).build().get()
        .uri(uriBuilder -> uriBuilder
            .path("exchangerates/rates/{table}/{code}")
            .build(CONVERSION_TABLE_TYPE, currency))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(ExchangeClientResponseDto.class);

  }

}
