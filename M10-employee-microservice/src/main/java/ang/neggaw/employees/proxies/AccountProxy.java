package ang.neggaw.employees.proxies;

import ang.neggaw.employees.proxyModels.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ANG
 * @since 05-08-2021 19:56
 */

@FeignClient(name = "account-microservice")
public interface AccountProxy {

    @GetMapping
    Account getAccountById(String accountNumber);
}
