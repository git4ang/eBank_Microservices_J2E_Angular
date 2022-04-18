package ang.neggaw.restControllers;

import ang.neggaw.entities.UserReact;
import ang.neggaw.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * author by: ANG
 * since: 18/04/2022 18:18
 */

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/api/users")
@RestController
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public Mono<Object> createUser(@Valid @RequestBody UserReact pUser) {
        log.info("Creating User ... {}", pUser);
        return userService.createUser(pUser)
                .map(userReact -> {
                    log.info("User with id: '{}' CREATED successfully", pUser.getIdUser());
                    return ResponseEntity.status(HttpStatus.CREATED).body(userReact);
                })
                .cast(Object.class)
                .onErrorResume(e -> {
                    log.error("Unable to create USER: {}.", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
                });
    }

    @GetMapping("/{idUser}")
    public Mono<Object> getUserById(@PathVariable(value = "idUser") String idUser) {
        log.info("Fetching USER with idUser: '{}'...", idUser);
        return userService.getUserById(idUser)
                .map(userReact -> {
                    log.info("User: {}.", userReact);
                    return ResponseEntity.status(HttpStatus.OK).body(userReact);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @GetMapping("/usernames/{username}")
    public Mono<Object> getUserByUsername(@PathVariable(value = "username") String username) {
        log.info("Fetching USER with username: '{}'...", username);
        return userService.getUserByUsername(username)
                .map(userReact -> {
                    log.info("User: {}.", userReact);
                    return ResponseEntity.status(HttpStatus.OK).body(userReact);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @GetMapping("/emails/{email}")
    public Mono<Object> getUserByEmail(@PathVariable(value = "email") String email) {
        log.info("Fetching USER with email: '{}'...", email);
        return userService.getUserByEmail(email)
                .map(userReact -> {
                    log.info("User: {}.", userReact);
                    return ResponseEntity.status(HttpStatus.OK).body(userReact);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @GetMapping
    public Flux<Object> getAllUsers() {
        System.out.println("Fetching all users ...");
        return userService.getAllUsers()
                .map(userReact -> ResponseEntity.status(HttpStatus.OK).body(userReact))
                .cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Flux.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage()));
                });
    }

    @PutMapping("/{idUser}")
    public Mono<Object> updateUser(@RequestBody UserReact userReact, @Valid @PathVariable String idUser) {

        log.info("Updating User with '{}'...", idUser);
        return this.userService.updateUser(idUser, userReact)
                .map(userUpdated -> {
                    log.info("User with idUser: '{}' UPDATED successfully", idUser);
                    return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error("Unable to update: {}.", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
                });
    }

    @PutMapping("/{idUser}/{idRole}/{isRemoved}")
    public Mono<Object> addRemoveUserToRole(@PathVariable String idUser,
                                            @PathVariable String idRole,
                                            @PathVariable boolean isRemoved) {

        log.info("Adding/Removing Role with ID: '{}' to/from User with ID: '{}'...", idRole, idUser);
        return this.userService.addRemoveUserToRole(idUser, idRole, isRemoved)
                .map(userDB -> {
                    log.info("User with ID: '{}' UPDATED successfully.", idUser);
                    return ResponseEntity.status(HttpStatus.OK).body(userDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
                });
    }

    @DeleteMapping("/{idUser}")
    public Mono<Object> deleteUserById(@PathVariable String idUser) {

        log.info("Deleting User with ID: '{}'...", idUser);
        return this.userService.deleteUserById(idUser)
                .map(userDB -> {
                    log.info("User with ID: '{}' DELETED successfully.", idUser);
                    return ResponseEntity.status(HttpStatus.OK).body(userDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
                });
    }

    @DeleteMapping("/usernames")
    public Mono<Object> deleteUserByUsername(@RequestParam(value = "username") String username) {

        log.info("Deleting User with username: '{}'...", username);
        return this.userService.deleteUserByUsername(username)
                .map(userDB -> {
                    log.info("User with username: '{}' DELETED successfully.", username);
                    return ResponseEntity.status(HttpStatus.OK).body(userDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
                });
    }

    @DeleteMapping("/emails/{email}")
    public Mono<Object> deleteUserByEmail(@PathVariable String email) {

        log.info("Deleting User with email: '{}'...", email);
        return this.userService.deleteUserByEmail(email)
                .map(userDB -> {
                    log.info("User with email: '{}' DELETED successfully.", email);
                    return ResponseEntity.status(HttpStatus.OK).body(userDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
                });
    }
}
