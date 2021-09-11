package ang.neggaw.operations.proxyModels;

import ang.neggaw.operations.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author by: ANG
 * since: 11/09/2021 08:00
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Integer idEmployee;

    private String name;

    private String email;

    private int idDepartment;

    private int idEmployeeBoss;

    @Enumerated(EnumType.STRING)
    private Operation.EntityState entityState;
}