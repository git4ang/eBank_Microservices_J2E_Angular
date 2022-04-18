package ang.neggaw.repositories;

import ang.neggaw.entities.UserReact;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * author by: ANG
 * since: 18/04/2022 18:06
 */

public interface UserRepository extends ReactiveMongoRepository<UserReact, String> {
    Mono<UserReact> findByIdUser(String idUser);
    Mono<UserReact> findByUsername(String username);
    Mono<UserReact> findByEmail(String email);
}

