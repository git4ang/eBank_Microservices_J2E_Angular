package ang.neggaw.operations.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

/**
 * @author by: ANG
 * since: 11/09/2021 07:58
 */

@Entity
@AllArgsConstructor
@Builder
@DiscriminatorValue(value = "D")
@XmlType(name = "deposit")
public class Deposit extends Operation {
}