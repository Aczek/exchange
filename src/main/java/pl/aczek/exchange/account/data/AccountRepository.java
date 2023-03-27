package pl.aczek.exchange.account.data;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.aczek.exchange.account.domain.Owner;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

  Optional<AccountEntity> findByOwner(Owner owner);

}
