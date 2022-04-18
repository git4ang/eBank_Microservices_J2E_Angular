package ang.neggaw.restControllers;

import ang.neggaw.entities.RoleReact;
import ang.neggaw.services.RoleService;
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
@RequestMapping(value = "/api/roles")
@RestController
public class RoleRestController {

    private final RoleService roleService;

    @PostMapping
    public Mono<Object> createRole(@Valid @RequestBody RoleReact pRole) {

        log.info("Creating Role...{}", pRole);
        return roleService.createRole(pRole)
                .map(roleReact -> {
                    log.info("Role with idRole: '{}' CREATED successfully", pRole.getIdRole());
                    return ResponseEntity.status(HttpStatus.CREATED).body(roleReact);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error("Error creating Role: {}.", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
                });
    }

    @GetMapping("/{idRole}")
    public Mono<Object> getRoleById(@PathVariable String idRole) {

        log.info("Fetching Role with ID: '{}'...", idRole);
        return this.roleService.getRoleById(idRole)
                .map(roleDB -> {
                    log.info("Role Found: {}.", roleDB);
                    return ResponseEntity.status(HttpStatus.FOUND).body(roleDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @GetMapping("/roleNames/{roleName}")
    public Mono<Object> getRoleByRoleName(@PathVariable String roleName) {

        log.info("Fetching Role with roleName: '{}'...", roleName);
        return this.roleService.getRoleByRoleName(roleName)
                .map(roleDB -> {
                    log.info("Role Found: {}.", roleDB);
                    return ResponseEntity.status(HttpStatus.FOUND).body(roleDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @GetMapping
    public Flux<Object> getAllRoles() {

        log.info("Fetching all roles...");
        return this.roleService.getAllRoles()
                .map(roles -> {
                    log.info("Roles: {}.", roles);
                    return ResponseEntity.status(HttpStatus.FOUND).body(roles);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage()));
                });
    }

    @PutMapping("/{idRole}")
    public Mono<Object> updateRole(@PathVariable String idRole, @Valid @RequestBody RoleReact role) {

        log.info("Updating Role with ID: '{}'...", idRole);
        return this.roleService.updateRole(idRole, role)
                .map(roleDB -> {
                    log.info("Role with ID: '{}' UPDATED successfully.", roleDB.getIdRole());
                    return ResponseEntity.status(HttpStatus.FOUND).body(roleDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @DeleteMapping("/{idRole}")
    public Mono<Object> deleteRoleById(@PathVariable String idRole) {

        log.info("Deleting Role with ID: '{}'...", idRole);
        return this.roleService.deleteRoleById(idRole)
                .map(roleDB -> {
                    log.info("Role with ID: '{}' DELETED successfully.", idRole);
                    return ResponseEntity.status(HttpStatus.FOUND).body(roleDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }

    @DeleteMapping("/roleNames/{roleName}")
    public Mono<Object> deleteRoleByRoleName(@PathVariable String roleName) {

        log.info("Deleting Role with roleName: '{}'...", roleName);
        return this.roleService.deleteRoleByRoleName(roleName)
                .map(roleDB -> {
                    log.info("Role with roleName: '{}' DELETED successfully.", roleName);
                    return ResponseEntity.status(HttpStatus.FOUND).body(roleDB);
                }).cast(Object.class)
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
                });
    }
}
