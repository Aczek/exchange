package pl.aczek.exchange.account


import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.aczek.exchange.account.data.AccountEntity
import pl.aczek.exchange.account.data.AccountRepository
import pl.aczek.exchange.account.data.CurrencyAccountEmbed
import pl.aczek.exchange.account.domain.Owner
import pl.aczek.exchange.account.dto.AccountCreationRequest
import pl.aczek.exchange.account.dto.ExchangeRequest
import pl.aczek.exchange.exchange.rate.ExchangeClient
import pl.aczek.exchange.exchange.rate.ExchangeRate
import pl.aczek.exchange.exchange.rate.ExchangeRateRequest
import spock.lang.Specification

@SpringBootTest
class AccountServiceTest extends Specification {

    @SpringBean
    AccountRepository accountRepository = Mock()

    @SpringBean
    ExchangeClient exchangeClient = Mock()

    @Autowired
    AccountService accountService


    def "OpenAccount"() {
        given:
        def uuid = UUID.randomUUID()
        AccountEntity accountEntity = new AccountEntity()
        accountEntity.setAccountId(uuid)
        def accountCreationRequest = AccountCreationRequest.builder()
                .firstName("Adam")
                .lastName("Nijaki")
                .initialBalance(BigDecimal.valueOf(100))
                .build()

        accountRepository.save(_ as AccountEntity) >> accountEntity
        accountRepository.findByOwner(_ as Owner) >> Optional.empty()

        when:
        def result = accountService.openAccount(accountCreationRequest)
        then:
        result == uuid

    }

    def "GetAccount"() {
        given:
        def uuid = UUID.randomUUID()
        def ownerVar = Owner.from("Adam", "Nijaki")
        def accountEntity = getAccountEntitiy(uuid, ownerVar)
        accountRepository.findById(_ as UUID) >> Optional.of(accountEntity)
        when:
        def account = accountService.getAccount(uuid)

        then:
        with(account) {
            accountId == uuid
            account.getOwner() == ownerVar
            currencyAccounts.get(Currency.getInstance("PLN")).getBalance() == BigDecimal.valueOf(100)

        }


    }

    private AccountEntity getAccountEntitiy(UUID uuid, Owner ownerVar) {

        CurrencyAccountEmbed currencyAccountEntity = new CurrencyAccountEmbed()
        currencyAccountEntity.setBalance(BigDecimal.valueOf(100))
        currencyAccountEntity.setCurrency(Currency.getInstance("PLN"))
        AccountEntity accountEntity = new AccountEntity()
        accountEntity.setAccountId(uuid)
        accountEntity.setOwner(ownerVar)
        accountEntity.setCurrencyAccounts(Set.of(currencyAccountEntity))
        return accountEntity
    }

    def "Exchange"() {
        given:
        def uuid = UUID.randomUUID()
        def ownerVar = Owner.from("Adam", "Nijaki")
        def accountEntity = getAccountEntitiy(uuid, ownerVar)
        def exchangeRequest = ExchangeRequest.from(Currency.getInstance("PLN"), Currency.getInstance("USD"), BigDecimal.valueOf(10))

        def exchangeRate = ExchangeRate.builder()
                .from(Currency.getInstance("PLN"))
                .to(Currency.getInstance("USD"))
                .conversionRate(BigDecimal.valueOf(0.25))
                .build()

        accountRepository.save(_ as AccountEntity) >> accountEntity
        accountRepository.findById(_ as UUID) >> Optional.of(accountEntity)
        exchangeClient.getExchangeRate(_ as ExchangeRateRequest) >> exchangeRate
        when:
        def acc = accountService.exchange(uuid, exchangeRequest)
        then:
        with(acc) {
            currencyAccounts.size() == 2
            currencyAccounts.get(Currency.getInstance("PLN")).getBalance() == BigDecimal.valueOf(90)
            currencyAccounts.get(Currency.getInstance("USD")).getBalance() == BigDecimal.valueOf(2.50)
        }
    }
}
