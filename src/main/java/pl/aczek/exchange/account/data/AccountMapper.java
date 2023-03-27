package pl.aczek.exchange.account.data;

import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import pl.aczek.exchange.account.domain.Account;
import pl.aczek.exchange.account.domain.CurrencyAccount;

@UtilityClass
public class AccountMapper {

  public static Account map(AccountEntity accountEntity) {
    return Account.builder()
        .accountId(accountEntity.getAccountId())
        .owner(accountEntity.getOwner())
        .currencyAccounts(accountEntity.getCurrencyAccounts()
            .stream()
            .map(AccountMapper::map)
            .collect(Collectors.toMap(CurrencyAccount::getCurrency, Function.identity())))
        .build();
  }


  private static CurrencyAccount map(CurrencyAccountEmbed currencyAccountEmbed) {
    return CurrencyAccount.builder()
        .currency(currencyAccountEmbed.getCurrency())
        .balance(currencyAccountEmbed.getBalance())
        .build();
  }

  public static AccountEntity map(Account account) {
    var accountEnt = new AccountEntity();
    accountEnt.setOwner(account.getOwner());
    accountEnt.setAccountId(account.getAccountId());
    accountEnt.setCurrencyAccounts(account.getCurrencyAccounts().values().stream().map(
        AccountMapper::map).collect(
        Collectors.toSet()));
    return accountEnt;
  }

  private static CurrencyAccountEmbed map(CurrencyAccount currencyAccount) {
    var currencyAccountEnt = new CurrencyAccountEmbed();
    currencyAccountEnt.setCurrency(currencyAccount.getCurrency());
    currencyAccountEnt.setBalance(currencyAccount.getBalance());
    return currencyAccountEnt;
  }

}
