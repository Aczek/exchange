package pl.aczek.exchange.controller;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.aczek.exchange.account.AccountService;
import pl.aczek.exchange.account.dto.AccountCreationRequest;
import pl.aczek.exchange.account.dto.AccountCreationResponse;
import pl.aczek.exchange.account.dto.AccountDto;
import pl.aczek.exchange.account.dto.ExchangeRequest;

@RestController
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping("/account/{id}")
  public AccountDto getAccount(@PathVariable("id") String accountId) {
    final var accountUuid = UUID.fromString(accountId);
    return AccountDto.from(accountService.getAccount(accountUuid));
  }

  @PostMapping("/account")
  public AccountCreationResponse openAccount(
      @RequestBody AccountCreationRequest accountCreationRequest) {
    final var openedAccount = accountService.openAccount(accountCreationRequest);
    return AccountCreationResponse.builder()
        .accountId(openedAccount.toString())
        .build();
  }

  @PostMapping("/account/{id}/exchange")
  public AccountDto exchange(@PathVariable("id") UUID accountId,
      @Valid @RequestBody ExchangeRequest exchangeRequest) {
    final var acc = accountService.exchange(accountId, exchangeRequest);
    return AccountDto.from(acc);
  }


}
