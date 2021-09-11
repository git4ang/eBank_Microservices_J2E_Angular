package ang.neggaw.operations.entities;

import ang.neggaw.operations.proxyModels.Account;
import ang.neggaw.operations.proxyModels.Employee;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;
import java.util.Date;

/**
 * @author by: ANG
 * since: 11/09/2021 07:57
 */

@Entity
@Table(name = "operations")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "operationType", discriminatorType = DiscriminatorType.STRING, length = 2)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "operation_type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "W", value = Withdrawal.class),
        @JsonSubTypes.Type(name = "D", value = Deposit.class)
})
@XmlSeeAlso(value = {
        Withdrawal.class,
        Deposit.class
})
public abstract class Operation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationNumber;

    @Temporal(TemporalType.DATE)
    private Date dateOperation;

    private double amount;

    private String description;

    @Positive
    private long accountNumber;

    @Transient
    private Account account;

    @Positive
    private long idEmployee;

    @Transient
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private EntityState entityState;

    public enum EntityState { CREATED, UPDATED, DELETED, PROCESSING }
}
