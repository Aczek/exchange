package pl.aczek.exchange.account.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Owner {

  private String firstName;
  private String lastName;

  public static Owner from(String firstName, String lastName) {
    return Owner.builder()
        .firstName(firstName)
        .lastName(lastName)
        .build();
  }

}
