package ang.neggaw.accounts.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

/**
 * @author by: ANG
 * @since: 09/09/2021 20:48
 */

@Entity
@Table(name = "saving_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "SA")
@XmlType(name = "SA")
public class SavingAccount extends Account {
    private Double interestRate;
}

