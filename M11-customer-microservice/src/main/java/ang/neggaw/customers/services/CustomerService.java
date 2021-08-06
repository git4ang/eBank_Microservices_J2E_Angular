package ang.neggaw.customers.services;

import ang.neggaw.customers.entities.Customer;

import java.util.List;

/**
 * @author ANG
 * @since 06-08-2021 14:41
 */

public interface CustomerService {
    Object createCustomer(final Customer customer);
    Customer getCustomer(final int idCustomer);
    List<Customer> allCustomers(final int offset, final int count);
    Object updateCustomer(Customer customer);
    Object updateCustomer_employee(final int idCustomer, final int idEmployee, final boolean idRemoved);
    String deleteCustomer(final int idCustomer);
}
