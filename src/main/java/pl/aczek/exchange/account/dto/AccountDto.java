package pl.aczek.exchange.account.dto;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.aczek.exchange.account.domain.Account;

@AllArgsConstructor
@Getter
public class AccountDto {

  UUID accountId;
  OwnerDto owner;
  Set<CurrencyAccountDto> currencyAccounts;

  public static AccountDto from(Account account) {
    return new AccountDto(account.getAccountId(),
        OwnerDto.from(account.getOwner()),
        account.getCurrencyAccounts().values()
            .stream()
            .map(CurrencyAccountDto::from)
            .collect(Collectors.toSet()));
  }

}
