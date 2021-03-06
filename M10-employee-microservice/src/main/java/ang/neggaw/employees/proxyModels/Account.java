package ang.neggaw.employees.proxyModels;

import ang.neggaw.employees.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author ANG
 * @since 05-08-2021 19:49
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String accountNumber;

    private double balance;

    private Date dateCreation;

    private Customer customer;

    private Employee employee;

    @Enumerated(EnumType.STRING)
    private Employee.EntityState entityState;
}
