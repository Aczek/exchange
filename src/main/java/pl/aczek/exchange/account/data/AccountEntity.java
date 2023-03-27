package pl.aczek.exchange.account.data;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import pl.aczek.exchange.account.domain.Owner;

@Entity(name = "account")
@Getter
@Setter
public class AccountEntity {

  @Id
  @GeneratedValue
  private UUID accountId;
  @Embedded
  private Owner owner;

  @ElementCollection(targetClass = CurrencyAccountEmbed.class)
  @CollectionTable(
      name = "currency_account",
      joinColumns = @JoinColumn(name = "account_id")
  )
  private Set<CurrencyAccountEmbed> currencyAccounts;

}
