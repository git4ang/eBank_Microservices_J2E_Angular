package ang.neggaw.customers.services.implementations;

import ang.neggaw.customers.entities.Customer;
import ang.neggaw.customers.services.CustomerService;

import java.util.List;

/**
 * @author ANG
 * @since 06-08-2021 14:41
 */

public class CustomerServiceImpl implements CustomerService {


    @Override
    public Object createCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomer(int idCustomer) {
        return null;
    }

    @Override
    public List<Customer> allCustomers(int offset, int count) {
        return null;
    }

    @Override
    public Object updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public Object updateCustomer_employee(int idCustomer, int idEmployee, boolean idRemoved) {
        return null;
    }

    @Override
    public String deleteCustomer(int idCustomer) {
        return null;
    }
}
