package ang.neggaw.customers.restControllers;

import ang.neggaw.customers.entities.Customer;
import ang.neggaw.customers.graphqls.CustomerQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by: ANG
 * since: 10/09/2021 09:38
 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/customers")
@RestController
public class CustomerRestController {

    private final CustomerQuery customerQuery;

    @GetMapping(value = "/{idCustomer}")
    public Customer getCustomerById(@PathVariable(value = "idCustomer") final int idCustomer) {
        return customerQuery.getCustomer(idCustomer);
    }
}