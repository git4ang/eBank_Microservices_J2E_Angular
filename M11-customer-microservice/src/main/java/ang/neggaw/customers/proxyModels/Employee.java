package ang.neggaw.customers.proxyModels;

import ang.neggaw.customers.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author ANG
 * @since 06-08-2021 15:04
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
    private Customer.EntityState entityState;
}
