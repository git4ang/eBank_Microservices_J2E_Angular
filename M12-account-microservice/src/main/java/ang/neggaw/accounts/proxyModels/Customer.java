package ang.neggaw.accounts.proxyModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.event.internal.EntityState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @author by: ANG
 * since: 10/09/2021 09:02
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Integer idCustomer;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private int idEmployee;

    private List<String> accountNumbers;

    @Enumerated(EnumType.STRING)
    private EntityState entityState;
}