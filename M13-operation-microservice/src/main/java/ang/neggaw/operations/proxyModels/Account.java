package ang.neggaw.operations.proxyModels;

import ang.neggaw.operations.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author by: ANG
 * since: 11/09/2021 07:59
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long accountNumber;

    private double balance;

    private Date dateCreation;

    private Long idCustomer;

    private Long idEmployee;

    @Enumerated(EnumType.STRING)
    private Operation.EntityState entityState;
}
