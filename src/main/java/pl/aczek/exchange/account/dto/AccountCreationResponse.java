package pl.aczek.exchange.account.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountCreationResponse {

  String accountId;
}
