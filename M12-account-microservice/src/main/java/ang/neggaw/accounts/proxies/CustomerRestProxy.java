package ang.neggaw.accounts.proxies;

import ang.neggaw.accounts.proxyModels.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by: ANG
 * since: 10/09/2021 09:03
 */

@FeignClient(name = "customer-microservice", url = "http://localhost:8200")
@RequestMapping(value = "/api/customers")
public interface CustomerRestProxy {

    @GetMapping(value = "/{idCustomer}")
    Customer getCustomerById(@PathVariable(value = "idCustomer") final int idCustomer);
}

