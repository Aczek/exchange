package pl.aczek.exchange.exchange.rate

import pl.aczek.exchange.account.dto.ExchangeRequest
import pl.aczek.exchange.rate.ExchangeRate
import spock.lang.Specification

import java.math.RoundingMode

class ExchangeRateTest extends Specification {
    def "givenCorrectExchangeRate_shouldCalculateProperCurrencyAmount"() {
        given:
        def exchangeRate = ExchangeRate.builder()
                .from(Currency.getInstance("PLN"))
                .to(Currency.getInstance("USD"))
                .conversionRate(BigDecimal.valueOf(BigDecimal.ONE.divide(BigDecimal.valueOf(4.3409), 30, RoundingMode.HALF_UP)))
                .build()

        def exchangeRequest = ExchangeRequest.from(
                Currency.getInstance("PLN"),
                Currency.getInstance("USD"),
                BigDecimal.valueOf(4.3409)
        )

        when:
        def result = exchangeRate.getAmountAfterConversion(exchangeRequest)
        then:
        result == BigDecimal.valueOf(BigDecimal.ONE)
    }

    def "givenCorrectExchangeRate_shouldCalculateProperCurrencyAmountAnotherDirection"() {
        given:
        def exchangeRate = ExchangeRate.builder()
                .from(Currency.getInstance("USD"))
                .to(Currency.getInstance("PLN"))
                .conversionRate(BigDecimal.valueOf(4.2549))
                .build()

        def exchangeRequest = ExchangeRequest.from(
                Currency.getInstance("USD"),
                Currency.getInstance("PLN"),
                BigDecimal.valueOf(2)
        )

        when:
        def result = exchangeRate.getAmountAfterConversion(exchangeRequest)
        then:
        result == BigDecimal.valueOf(8.51)
    }

    def "givenIncorrectCurrencyOrder_shouldThrowException"() {
        given:
        def exchangeRate = ExchangeRate.builder()
                .from(Currency.getInstance("PLN"))
                .to(Currency.getInstance("USD"))
                .conversionRate(BigDecimal.valueOf(2))
                .build()

        def exchangeRequest = ExchangeRequest.from(
                Currency.getInstance("USD"),
                Currency.getInstance("PLN"),
                BigDecimal.valueOf(5)
        )
        when:
        exchangeRate.getAmountAfterConversion(exchangeRequest)
        then:
        thrown IllegalArgumentException
    }
}
