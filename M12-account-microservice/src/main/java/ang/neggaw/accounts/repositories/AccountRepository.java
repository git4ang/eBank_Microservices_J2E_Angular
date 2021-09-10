package ang.neggaw.accounts.repositories;

import ang.neggaw.accounts.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author by: ANG
 * since: 09/09/2021 20:49
 */

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(long accountNumber);
}
