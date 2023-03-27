package pl.aczek.exchange.exchange.rate


import pl.aczek.exchange.account.dto.ExchangeRequest
import pl.aczek.exchange.rate.ExchangeClient
import pl.aczek.exchange.rate.ExchangeRateRequest
import spock.lang.Specification


class ExchangeClientIntegrationTest extends Specification {


    def "givenPLNtoUSDExchange_thenCorrectOrderOfRateAndUsdWorthMoreThanPln"() {
        given:
        ExchangeClient exchangeClient1 = new ExchangeClient()
        ExchangeRateRequest exchangeRateRequest = getExchangeRateRequest("PLN", "USD")
        when:
        var exchangeRate = exchangeClient1.getExchangeRate(exchangeRateRequest)
        then:
        exchangeRate.getFrom() == Currency.getInstance("PLN")
        exchangeRate.to == Currency.getInstance("USD")
        exchangeRate.conversionRate.compareTo(BigDecimal.ONE) == -1

    }

    def "givenUSDtoPLNExchange_thenCorrectOrderOfRateAndPlnWorthLessThanUsd"() {
        given:
        ExchangeClient exchangeClient = new ExchangeClient()
        ExchangeRateRequest exchangeRateRequest = getExchangeRateRequest("USD", "PLN")
        when:
        var exchangeRate = exchangeClient.getExchangeRate(exchangeRateRequest)

        then:
        exchangeRate.from == Currency.getInstance("USD")
        exchangeRate.to == Currency.getInstance("PLN")
        exchangeRate.conversionRate.compareTo(BigDecimal.ONE) == 1
    }

    private ExchangeRateRequest getExchangeRateRequest(String fromString, String toString) {
        Currency from = Currency.getInstance(fromString)
        Currency to = Currency.getInstance(toString)
        ExchangeRequest exchangeRequest = ExchangeRequest.from(from, to, BigDecimal.ONE)

        ExchangeRateRequest exchangeRateRequest = ExchangeRateRequest.from(exchangeRequest)
        exchangeRateRequest
    }
}
