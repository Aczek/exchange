package pl.aczek.exchange.account.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.aczek.exchange.account.domain.Account;
import pl.aczek.exchange.account.domain.CurrencyAccount;

@AllArgsConstructor
@Getter
public class AccountDto {

  UUID accountId;
  OwnerDto owner;
  Set<CurrencyAccount> currencyAccounts;

  public static AccountDto from(Account account) {
    return new AccountDto(account.getAccountId(),
        OwnerDto.from(account.getOwner()),
        new HashSet<>(account.getCurrencyAccounts().values()));
  }

}
