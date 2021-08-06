package ang.neggaw.employees.proxies;

import ang.neggaw.employees.proxyModels.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ANG
 * @since 05-08-2021 19:56
 */

@FeignClient(name = "customer-microservice", url = "http://localhost:8200")
public interface CustomerRestProxy {

    @GetMapping(value = "/{idCustomer}")
    Customer getCustomerById(@PathVariable(value = "idCustomer") final int idCustomer);
}
