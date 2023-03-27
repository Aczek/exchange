package pl.aczek.exchange.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.NonNull;


@Getter
@Setter
@Builder
@Jacksonized
public class AccountCreationRequest {

  @NotEmpty
  String firstName;
  @NotEmpty
  String lastName;
  @NonNull
  @DecimalMin("0.0")
  @Digits(integer = 255, fraction = 2)
  BigDecimal initialBalance;

}
