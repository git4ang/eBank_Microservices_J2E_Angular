package ang.neggaw.employees.proxies;

import ang.neggaw.employees.proxyModels.Customer;

/**
 * @author ANG
 * @since 05-08-2021 19:56
 */

public interface CustomerProxy {
    Customer getCustomerById(int idCustomer);
}
