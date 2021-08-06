package ang.neggaw.customers.proxies;

import ang.neggaw.customers.proxyModels.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ANG
 * @since 06-08-2021 15:05
 */

@FeignClient(name = "LB://ACCOUNT-MICROSERVICE", url = "localhost:8300")
@RequestMapping(value = "/api/accounts")
public interface AccountRestProxy {

    @GetMapping(value = "/{accountNumber}")
    Account getAccountById(@PathVariable(value = "accountNumber") final String accountNumber);

    @DeleteMapping(value = "/{accountNumber}")
    Account deleteAccount(@PathVariable(value = "accountNumber") final String accountNumber);
}
