package ang.neggaw.customers.graphqls;

import ang.neggaw.customers.entities.Customer;
import ang.neggaw.customers.services.CustomerService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ANG
 * @since 06-08-2021 15:56
 */

@Log4j2
@RequiredArgsConstructor
@Component
public class CustomerQuery implements GraphQLQueryResolver {

    private final CustomerService customerService;

    public Customer customer(final int idCustomer) {

        log.info("Fetching Customer with id: '{}'.", idCustomer);
        Customer customer = customerService.getCustomer(idCustomer);
        if(customer == null) {
            log.error("Unable to find Customer with id: '{}'.", idCustomer);
            return null;
        }

        return customer;
    }

    public List<Customer> customers(final int offset, final int count) {

        log.info("Fetching all customers ...");
        return customerService.allCustomers(offset, count);
    }
}
