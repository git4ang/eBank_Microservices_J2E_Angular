package ang.neggaw.operations.proxies;

import ang.neggaw.operations.proxyModels.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by: ANG
 * since: 11/09/2021 08:16
 */

@FeignClient(name = "LB://ACCOUNT-MICROSERVICE", url = "localhost:8300")
@RequestMapping(value = "/api/accounts")
public interface AccountRestProxy {

    @GetMapping(value = "/{accountNumber}")
    Account getAccountById(@PathVariable(value = "accountNumber") final long accountNumber);

    @DeleteMapping(value = "/{accountNumber}")
    String deleteAccount(@PathVariable(value = "accountNumber") final long accountNumber);
}
