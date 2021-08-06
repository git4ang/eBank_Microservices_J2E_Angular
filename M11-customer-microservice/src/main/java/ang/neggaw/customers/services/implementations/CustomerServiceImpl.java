package ang.neggaw.customers.services.implementations;

import ang.neggaw.customers.entities.Customer;
import ang.neggaw.customers.proxies.AccountRestProxy;
import ang.neggaw.customers.proxies.EmployeeRestProxy;
import ang.neggaw.customers.proxyModels.Employee;
import ang.neggaw.customers.repositories.CustomerRepository;
import ang.neggaw.customers.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ANG
 * @since 06-08-2021 14:41
 */

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRestProxy employeeRestProxy;
    private final AccountRestProxy accountRestProxy;

    @Override
    public Object createCustomer(Customer customer) {

        Employee employee = employeeRestProxy.getEmployeeById(customer.getIdEmployee());
        if(employee == null)
            return String.format("Unable to create. Employee with id: '%s' Not Found.", customer.getIdEmployee());
        customer.setEntityState(Customer.EntityState.CREATED);
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(final int idCustomer) {
        return customerRepository.findByIdCustomer(idCustomer);
    }

    @Override
    public List<Customer> allCustomers(int offset, int count) {
        offset = offset > count ? 0 : offset;
        count = Math.min(count, customerRepository.findAll().size());
        return customerRepository.findAll().subList(offset, count);
    }

    @Override
    public Object updateCustomer(final Customer customer) {

        Customer customerDB = getCustomer(customer.getIdCustomer());
        if(customerDB == null)
            return String.format("Unable to update. Customer with id: '%s' Not Found.", customer.getIdCustomer());

        customer.setIdEmployee(customerDB.getIdCustomer());
        customer.setAccountNumbers(customerDB.getAccountNumbers());
        customer.setEntityState(Customer.EntityState.UPDATED);
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Object updateCustomer_employee(final int idCustomer, final int idEmployee, final boolean isRemoved) {

        Customer customerDB = getCustomer(idCustomer);
        if(customerDB == null)
            return String.format("Unable to update Employee of Customer. Customer with id: '%s' Not Found.", idCustomer);

        Employee employee = employeeRestProxy.getEmployeeById(idEmployee);
        if(employee == null)
            return String.format("Unable to update (add/remove) Customer. Employee with id: '%s' Not Found.", idEmployee);

        customerDB.setEntityState(Customer.EntityState.UPDATED);
        if(isRemoved) {
            employeeRestProxy.updateEmployee_customer(idCustomer, idEmployee, true);
        } else {
            customerDB.setIdEmployee(idEmployee);
            employeeRestProxy.updateEmployee_customer(idCustomer, idEmployee, false);
        }
        return customerRepository.saveAndFlush(customerDB);
    }

    @Override
    public String deleteCustomer(final int idCustomer) {

        Customer customerDB = getCustomer(idCustomer);
        if(customerDB == null)
            return String.format("Unable to update Employee of Customer. Customer with id: '%s' Not Found.", idCustomer);

        if(!customerDB.getAccountNumbers().isEmpty())
            customerDB.getAccountNumbers().forEach(accountRestProxy::deleteAccount);
        employeeRestProxy.updateEmployee_customer(idCustomer, customerDB.getIdEmployee(), true);
        customerRepository.delete(customerDB);
        return String.format("Customer with id: '%s' DELETED successfully", idCustomer);
    }
}
