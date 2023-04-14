package pl.aczek.exchange.account.domain


import pl.aczek.exchange.account.dto.ExchangeRequest
import pl.aczek.exchange.rate.ExchangeRate
import spock.lang.Specification

class AccountsTest extends Specification {
    def "Exchange"() {
        given:
        def owner = Owner.from("Adam", "Nijaki")
        def currencyAccount = CurrencyAccount.from(Currency.getInstance("PLN"), BigDecimal.valueOf(50))
        def account = Account.from(owner, currencyAccount)

        def exchangeRequest = ExchangeRequest.from(Currency.getInstance("PLN"), Currency.getInstance("USD"), BigDecimal.valueOf(10))
        def exchangeRate = ExchangeRate.builder()
                .from(Currency.getInstance("PLN"))
                .to(Currency.getInstance("USD"))
                .conversionRate(BigDecimal.valueOf(2))
                .build()

        when:
        def acc = account.exchange(exchangeRequest, exchangeRate)
        then:
        with(acc) {
            currencyAccounts.size() == 2
            currencyAccounts.get(Currency.getInstance("PLN")).getBalance() == BigDecimal.valueOf(40)
            currencyAccounts.get(Currency.getInstance("USD")).getBalance() == BigDecimal.valueOf(20)
        }


    }
}
