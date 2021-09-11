package ang.neggaw.operations.repositories;

import ang.neggaw.operations.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author by: ANG
 * since: 11/09/2021 07:59
 */

@RepositoryRestResource
public interface OperationRepository extends JpaRepository<Operation, Long> {
    Operation findByOperationNumber(long operationNumber);
}
