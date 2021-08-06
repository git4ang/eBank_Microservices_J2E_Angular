package ang.neggaw.customers.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author ANG
 * @since 06-08-2021 14:40
 */

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCustomer;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private int idEmployee;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> accountNumbers;

    @Enumerated(EnumType.STRING)
    private EntityState entityState;

    public enum EntityState { CREATED, UPDATED, DELETED, PROCESSING }
}
