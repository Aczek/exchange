package pl.aczek.exchange.exchange.rate;

import java.util.Currency;
import lombok.Value;
import pl.aczek.exchange.account.dto.ExchangeRequest;

@Value
public class ExchangeRateRequest {

    Currency from;
    Currency to;

    public static ExchangeRateRequest from(ExchangeRequest exchangeRequest) {
        if (isConversionBetweenTheSameCurrency(exchangeRequest)) {
            throw new IllegalArgumentException();
        }
        return new ExchangeRateRequest(exchangeRequest.getFrom(), exchangeRequest.getTo());
    }

    private static boolean isConversionBetweenTheSameCurrency(ExchangeRequest exchangeRequest) {
        return exchangeRequest.getFrom().equals(exchangeRequest.getTo());
    }


}
