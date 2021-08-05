package ang.neggaw.employees.proxies;

import ang.neggaw.employees.proxyModels.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ANG
 * @since 05-08-2021 19:56
 */

@FeignClient(name = "customer-microservice")
public interface CustomerProxy {

    @GetMapping
    Customer getCustomerById(int idCustomer);
}
