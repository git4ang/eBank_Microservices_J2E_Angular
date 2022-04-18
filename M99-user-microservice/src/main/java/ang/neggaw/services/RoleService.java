package ang.neggaw.services;

import ang.neggaw.entities.RoleReact;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * author by: ANG
 * since: 18/04/2022 18:12
 */

public interface RoleService {
    Mono<RoleReact> createRole(RoleReact r);
    Mono<RoleReact> getRoleById(String idRole);
    Mono<RoleReact> getRoleByRoleName(String roleName);
    Flux<RoleReact> getAllRoles();
    Mono<RoleReact> updateRole(String idRole, RoleReact r);
    Mono<RoleReact> deleteRoleById(String idRole);
    Mono<RoleReact> deleteRoleByRoleName(String roleName);
}
