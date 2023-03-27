package pl.aczek.exchange.account;

import jakarta.validation.Valid;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aczek.exchange.account.data.AccountEntity;
import pl.aczek.exchange.account.data.AccountMapper;
import pl.aczek.exchange.account.data.AccountRepository;
import pl.aczek.exchange.account.domain.Account;
import pl.aczek.exchange.account.domain.CurrencyAccount;
import pl.aczek.exchange.account.domain.Owner;
import pl.aczek.exchange.account.dto.AccountCreationRequest;
import pl.aczek.exchange.account.dto.ExchangeRequest;
import pl.aczek.exchange.account.exception.AccountAlreadyExistsException;
import pl.aczek.exchange.account.exception.AccountNotFoundException;
import pl.aczek.exchange.exchange.rate.ExchangeClient;
import pl.aczek.exchange.exchange.rate.ExchangeRateRequest;

@Service
@RequiredArgsConstructor
public class AccountService {

  private static final String BASE_CURRENCY_CODE = "PLN";

  private final AccountRepository accountRepository;

  private final ExchangeClient exchangeClient;

  public UUID openAccount(@Valid AccountCreationRequest accountCreationRequest) {
    Owner owner = Owner.from(accountCreationRequest.getFirstName(),
        accountCreationRequest.getLastName());
    checkExistingAccount(owner);

    final Account account = prepareAccount(accountCreationRequest);
    AccountEntity acc = save(account);
    return acc.getAccountId();
  }

  public Account getAccount(UUID accountId) {
    final AccountEntity account = accountRepository.findById(accountId)
        .orElseThrow(AccountNotFoundException::new);
    return AccountMapper.map(account);
  }

  public Account exchange(UUID accountId, @Valid ExchangeRequest exchangeRequest) {
    Account acc = getAccount(accountId);

    final var exchangeRate = exchangeClient.getExchangeRate(
        ExchangeRateRequest.from(exchangeRequest));
    acc = acc.exchange(exchangeRequest, exchangeRate);
    save(acc);
    return acc;
  }

  private void checkExistingAccount(Owner owner) {
    Optional<AccountEntity> existingAccount = accountRepository.findByOwner(owner);
    existingAccount.ifPresent(s -> {
      throw new AccountAlreadyExistsException("account already in database, creation forbidden");
    });
  }

  private AccountEntity save(Account account) {
    AccountEntity accountToSave = AccountMapper.map(account);
    return accountRepository.save(accountToSave);
  }

  private static Account prepareAccount(AccountCreationRequest accountCreationRequest) {
    final var owner = Owner.from(accountCreationRequest.getFirstName(),
        accountCreationRequest.getLastName());
    final var currencyAccount = CurrencyAccount.from(Currency.getInstance(BASE_CURRENCY_CODE),
        accountCreationRequest.getInitialBalance());
    return Account.from(owner, currencyAccount);
  }


}
