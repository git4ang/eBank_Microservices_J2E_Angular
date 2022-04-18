package ang.neggaw.services.implementations;

import ang.neggaw.entities.UserReact;
import ang.neggaw.enums.EntityState;
import ang.neggaw.repositories.RoleRepository;
import ang.neggaw.repositories.UserRepository;
import ang.neggaw.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * author by: ANG
 * since: 18/04/2022 18:12
 */

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public Mono<UserReact> createUser(UserReact u) {

        return this.userRepository.findByIdUser(u.getIdUser())
                .flatMap(___ -> Mono.error(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Unable to create User. idUser: '%s' is already exists.", u.getIdUser()))))
                .cast(UserReact.class)

                .switchIfEmpty(Mono.defer(() -> this.userRepository.findByUsername(u.getUsername()))
                        .flatMap(___ -> Mono.error(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Unable to create User. Username: '%s' is already exists.", u.getUsername()))))
                        .cast(UserReact.class)

                        .switchIfEmpty(Mono.defer(() -> this.userRepository.findByEmail(u.getEmail()))
                                .flatMap(___ -> Mono.error(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Unable to create User. Email: : '%s' is already exists.", u.getEmail()))))
                                .cast(UserReact.class)

                                .switchIfEmpty(Mono.defer(() -> this.roleRepository.findByIdRole(u.getIdRole().toUpperCase()))
                                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to create User. User with idRole: '%s' Not Found.", u.getIdRole()))))
                                        .flatMap( roleReact -> {
                                            if(u.getPassword().equals(u.getRePassword())) {
                                                u.setEntityState(EntityState.CREATED);
                                                u.setPassword(passwordEncoder.encode(u.getPassword()));
                                                u.setLastPasswordResetDate(new Date());
                                                u.setEnabled(true);
                                                u.setIdRole(u.getIdRole());
                                                u.getAuthorities().add(roleReact.getRoleName().toUpperCase());
                                                return userRepository.save(u);
                                            } else
                                                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Passwords are not same."));
                                        })
                                ))
                );
    }

    @Override
    public Mono<UserReact> getUserById(String idUser) {

        return this.userRepository.findById(idUser)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to find User. User with idUser: '%s' Not Found", idUser))));
    }

    @Override
    public Mono<UserReact> getUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to find User. User with username: '%s' Not Found", username))));
    }

    @Override
    public Mono<UserReact> getUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to find User. User with email: '%s' Not Found", email))));
    }

    @Override
    public Flux<UserReact> getAllUsers() {
        return this.userRepository.findAll()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT, "List with empty Users")));
    }

    @Override
    public Mono<UserReact> updateUser(String idUser, UserReact u) {
        return this.userRepository.findByIdUser(idUser)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to update. User with idUser: '%s' not exists.", idUser))))
                .flatMap(userDB -> {
                    if(u.getIdUser().equals(idUser)) {
                        BeanUtils.copyProperties(u, userDB);
                        userDB.setEntityState(EntityState.UPDATED);
                        return userRepository.save(userDB);
                    } else
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT,
                                String.format("Unable to update. The idUser: '%s' is different to User with idUser: '%s'.", idUser, u.getIdUser())));
                });
    }

    @Override
    public Mono<UserReact> addRemoveUserToRole(String idUser, String idRole, boolean isRemoved) {

        return this.userRepository.findByIdUser(idUser)
                .flatMap(userDB -> this.roleRepository.findByIdRole(idRole)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Unable to ADD/Remove role to/from User. idUser: '%s' Not exists.", idUser))))
                        .flatMap(roleDB -> {
                            if(isRemoved && userDB.getAuthorities().contains(roleDB.getRoleName())) {
                                userDB.getAuthorities().removeIf(s -> s.equals(roleDB.getRoleName()));
                                roleDB.getUsernames().removeIf(s -> s.equals(userDB.getUsername()));
                            }
                            else if (isRemoved && !userDB.getAuthorities().contains(roleDB.getRoleName())) {
                                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        String.format("Unable to Add/Remove. Role with ID: '%s' Not exists in the User with ID: '%s'.", idRole, idUser)));
                            }
                            else if (!isRemoved && !userDB.getAuthorities().contains(roleDB.getRoleName())) {
                                userDB.getAuthorities().add(roleDB.getRoleName().toUpperCase());
                                roleDB.getUsernames().add(userDB.getUsername());
                            }
                            else if (!isRemoved && userDB.getAuthorities().contains(roleDB.getRoleName())) {
                                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        String.format("Unable to Add/Remove. Role with ID: '%s' already exists in the User with ID: '%s'.", idRole, idUser)));
                            }
                            roleDB.setEntityState(EntityState.UPDATED);
                            this.roleRepository.save(roleDB);
                            userDB.setEntityState(EntityState.UPDATED);
                            return this.userRepository.save(userDB);
                        })
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Unable to ADD/Remove role to/from User. idRole: '%s' Not exists.", idRole)))));
    }

    @Override
    public Mono<UserReact> deleteUserById(String idUser) {
        return this.userRepository.findByIdUser(idUser)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to delete. User with ID: '%s' Not Found.", idUser))))
                .flatMap(userDB -> {
                    userDB.setEntityState(EntityState.DELETED);
                    return this.userRepository.delete(userDB)
                            .then(Mono.just(userDB));
                });

    }

    @Override
    public Mono<UserReact> deleteUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to delete. User with username: '%s' Not Found.", username))))
                .flatMap(userDB -> {
                    userDB.setEntityState(EntityState.DELETED);
                    return this.userRepository.delete(userDB)
                            .then(Mono.just(userDB));
                });
    }

    @Override
    public Mono<UserReact> deleteUserByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to delete. User with email: '%s' Not Found.", email))))
                .flatMap(userDB -> {
                    userDB.setEntityState(EntityState.DELETED);
                    return this.userRepository.delete(userDB)
                            .then(Mono.just(userDB));
                });
    }
}
