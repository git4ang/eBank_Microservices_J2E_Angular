package ang.neggaw.employees.proxies;

import ang.neggaw.employees.proxyModels.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ANG
 * @since 05-08-2021 19:56
 */

@FeignClient(name = "LB://ACCOUNT-MICROSERVICE")
public interface AccountRestProxy {

    @GetMapping(value = "/{accountNumber}")
    Account getAccountById(@PathVariable(value = "accountNumber") final String accountNumber);
}
