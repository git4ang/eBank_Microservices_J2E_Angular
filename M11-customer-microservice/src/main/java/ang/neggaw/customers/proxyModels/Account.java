package ang.neggaw.customers.proxyModels;

import ang.neggaw.customers.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author ANG
 * @since 06-08-2021 15:04
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
    private Customer.EntityState entityState;
}
