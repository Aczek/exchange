package pl.aczek.exchange.account.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.aczek.exchange.account.domain.CurrencyAccount;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CurrencyAccountDto {

    private String currency;
    private BigDecimal balance;

    public static CurrencyAccountDto from(CurrencyAccount currencyAccount) {
        return CurrencyAccountDto.builder()
            .currency(currencyAccount.getCurrency().getCurrencyCode())
            .balance(currencyAccount.getBalance()).build();

    }

}
