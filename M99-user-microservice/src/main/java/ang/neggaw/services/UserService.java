package ang.neggaw.services;

import ang.neggaw.entities.UserReact;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * author by: ANG
 * since: 18/04/2022 18:12
 */

public interface UserService {
    Mono<UserReact> createUser(UserReact u);
    Mono<UserReact> getUserById(String idUser);
    Mono<UserReact> getUserByUsername(String username);
    Mono<UserReact> getUserByEmail(String email);
    Flux<UserReact> getAllUsers();
    Mono<UserReact> updateUser(String idUser, UserReact u);
    Mono<UserReact> addRemoveUserToRole(String idUser, String idRole, boolean removed);
    Mono<UserReact> deleteUserById(String idUser);
    Mono<UserReact> deleteUserByUsername(String username);
    Mono<UserReact> deleteUserByEmail(String email);
}
