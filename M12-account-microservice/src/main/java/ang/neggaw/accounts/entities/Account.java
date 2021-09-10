package ang.neggaw.accounts.entities;

import ang.neggaw.accounts.proxyModels.Customer;
import ang.neggaw.accounts.proxyModels.Employee;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author by: ANG
 * since: 09/09/2021 20:48
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "accountType", discriminatorType = DiscriminatorType.STRING, length = 2)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "account_type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "CA", value = CurrentAccount.class),
        @JsonSubTypes.Type(name = "SA", value = SavingAccount.class)
})
@XmlSeeAlso(value = { CurrentAccount.class, SavingAccount.class })
public abstract class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    private Double balance;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    private int idCustomer;

    @Transient
    private Customer customer;

    private int idEmployee;

    @Transient
    private Employee employee;

    @ElementCollection
    private List<Long> idsOperations;

    @Transient
    private String accountType;

    @Enumerated(EnumType.STRING)
    private EntityState entityState;

    public enum EntityState { CREATED, UPDATED, DELETED, PROCESSING }
}