package pl.aczek.exchange.account.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pl.aczek.exchange.account.dto.ExchangeRequest;
import pl.aczek.exchange.account.exception.CurrencyAccountNotFoundException;
import pl.aczek.exchange.account.exception.InsufficientFundsException;
import pl.aczek.exchange.account.exception.InvalidOperationException;
import pl.aczek.exchange.exchange.rate.ExchangeRate;

@Builder
@Getter
@ToString
@Slf4j
public class Account {

  private UUID accountId;
  private Owner owner;
  private Map<Currency, CurrencyAccount> currencyAccounts;

  public static Account from(Owner owner, CurrencyAccount currencyAccount) {
    return Account.builder()
        .owner(owner)
        .currencyAccounts(new HashMap<>(Map.of(currencyAccount.getCurrency(), currencyAccount)))
        .build();
  }

  public Account exchange(ExchangeRequest exchangeRequest, ExchangeRate exchangeRate) {
    if (exchangeRequest.getFrom().equals(exchangeRequest.getTo())) {
      throw new InvalidOperationException("exchange between the same currencies is not acceptable");
    }
    if (!currencyAccounts.containsKey(exchangeRequest.getFrom())) {
      throw new CurrencyAccountNotFoundException("missing currency account with currency for sale");
    }

    var currencyAccountFrom = currencyAccounts.get(exchangeRequest.getFrom());
    if (hasEnoughFundsOnCurrencyAccount(exchangeRequest, currencyAccountFrom)) {
      throw new InsufficientFundsException("insufficient funds on currency account for exchange");
    }
    var currencyAccountTo = currencyAccounts.getOrDefault(exchangeRequest.getTo(),
        CurrencyAccount.builder()
            .currency(exchangeRequest.getTo())
            .balance(BigDecimal.ZERO)
            .build());

    currencyAccountFrom = currencyAccountFrom.subtract(exchangeRequest.getAmount());
    currencyAccountTo = currencyAccountTo.add(
        exchangeRate.getAmountAfterConversion(exchangeRequest));
    currencyAccounts.put(currencyAccountFrom.getCurrency(), currencyAccountFrom);
    currencyAccounts.put(currencyAccountTo.getCurrency(), currencyAccountTo);
    log.info("exchange finished: {}", currencyAccounts);
    return this;

  }

  private static boolean hasEnoughFundsOnCurrencyAccount(ExchangeRequest exchangeRequest,
      CurrencyAccount currencyFrom) {
    return currencyFrom.getBalance().compareTo(exchangeRequest.getAmount()) < 0;
  }


}
