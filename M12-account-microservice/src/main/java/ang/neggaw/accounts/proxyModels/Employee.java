package ang.neggaw.accounts.proxyModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.event.internal.EntityState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author by: ANG
 * since: 10/09/2021 09:02
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
    private EntityState entityState;
}

