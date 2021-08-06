package ang.neggaw.customers.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
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

    @NotEmpty
    @NotBlank
    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotEmpty
    @NotBlank
    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    @NotBlank
    @NotEmpty
    private String address;

    @Positive
    private int idEmployee;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> accountNumbers;

    @Enumerated(EnumType.STRING)
    private EntityState entityState;

    public enum EntityState { CREATED, UPDATED, DELETED, PROCESSING }
}
