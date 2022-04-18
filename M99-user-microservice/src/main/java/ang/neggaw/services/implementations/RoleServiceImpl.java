package ang.neggaw.services.implementations;

import ang.neggaw.entities.RoleReact;
import ang.neggaw.enums.EntityState;
import ang.neggaw.repositories.RoleRepository;
import ang.neggaw.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * author by: ANG
 * since: 18/04/2022 18:12
 */

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Mono<RoleReact> createRole(RoleReact r) {

        return this.roleRepository.findByIdRole(r.getIdRole())
                .flatMap(___ -> Mono.error(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                        String.format("Unable to create Role. Role with idRole '%s' is already exists.", r.getIdRole()))))
                .cast(RoleReact.class)

                .switchIfEmpty(Mono.defer(() -> this.roleRepository.findByRoleName(r.getRoleName().toUpperCase())))
                .flatMap(___ -> Mono.error(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                        String.format("Unable to create Role. Role with '%s' is already exists.", r.getRoleName()))))
                .cast(RoleReact.class)

                .switchIfEmpty(Mono.defer(() -> {
                    r.setEntityState(EntityState.CREATED);
                    r.setRoleName(r.getRoleName().toUpperCase());
                    return roleRepository.save(r);
                }));
    }

    @Override
    public Mono<RoleReact> getRoleById(String idRole) {
        return this.roleRepository.findByIdRole(idRole)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to find Role. Role with ID: '%s' Not Found.", idRole))));
    }

    @Override
    public Mono<RoleReact> getRoleByRoleName(String roleName) {
        return this.roleRepository.findByRoleName(roleName)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to find Role. Role with roleName: '%s' Not Found.", roleName))));
    }

    @Override
    public Flux<RoleReact> getAllRoles() {
        return this.roleRepository.findAll()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT, "There aren't any role in DB.")));
    }

    @Override
    public Mono<RoleReact> updateRole(String idRole, RoleReact r) {
        return this.roleRepository.findByIdRole(idRole)
                .flatMap(roleDB -> {
                    if(idRole.equals(r.getIdRole())){
                        BeanUtils.copyProperties(roleDB, r);
                        return this.roleRepository.save(r);
                    } else
                        return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT,
                                String.format("The idRole: '%s' is different to DB idRole: '%s'.", idRole, r.getIdRole())));
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to Update. Role with idRole: '%s' Not Found.", idRole))));
    }

    @Override
    public Mono<RoleReact> deleteRoleById(String idRole) {
        return this.roleRepository.findByIdRole(idRole)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to delete. Role with idRole: '%s' Not Found.", idRole))))
                .flatMap(roleDB -> {
                    roleDB.setEntityState(EntityState.DELETED);
                    return this.roleRepository.delete(roleDB)
                            .then(Mono.just(roleDB));
                });

    }

    @Override
    public Mono<RoleReact> deleteRoleByRoleName(String roleName) {

        return this.roleRepository.findByRoleName(roleName)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to delete. Role with roleName: '%s' Not Found.", roleName))))
                .flatMap(roleDB -> {
                    roleDB.setEntityState(EntityState.DELETED);
                    return this.roleRepository.delete(roleDB)
                            .then(Mono.just(roleDB));
                });
    }
}
