package ang.neggaw.customers.repositories;

import ang.neggaw.customers.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author ANG
 * @since 06-08-2021 14:41
 */

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByIdCustomer(int idCustomer);
}
