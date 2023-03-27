package pl.aczek.exchange.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.aczek.exchange.account.domain.Owner;

@Getter
@Setter
@AllArgsConstructor
public class OwnerDto {

  private String firstName;
  private String lastName;

  public static OwnerDto from(Owner owner) {
    return new OwnerDto(owner.getFirstName(), owner.getLastName());
  }

}
