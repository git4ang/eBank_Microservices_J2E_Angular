package ang.neggaw.repositories;

import ang.neggaw.entities.RoleReact;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * author by: ANG
 * since: 18/04/2022 18:06
 */

public interface RoleRepository extends ReactiveMongoRepository<RoleReact, String> {
    Mono<RoleReact> findByIdRole(String idRole);
    Mono<RoleReact> findByRoleName(String roleName);
}
