package ang.neggaw.employees.proxies;

import ang.neggaw.employees.proxyModels.Account;

/**
 * @author ANG
 * @since 05-08-2021 19:56
 */

public interface AccountProxy {
    Account getAccountById(String accountNumber);
}
