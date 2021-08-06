package ang.neggaw.customers.graphqls;

import ang.neggaw.customers.entities.Customer;
import ang.neggaw.customers.services.CustomerService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @author ANG
 * @since 06-08-2021 15:57
 */

@Log4j2
@RequiredArgsConstructor
@Component
public class CustomerMutation implements GraphQLMutationResolver {

    private final CustomerService customerService;

    public Customer createCustomer(final String name,
                                   final String phoneNumber,
                                   final String email,
                                   final String address,
                                   final int idEmployee) {

        log.info("Creating Customer with name '{}'", name);

        Customer c = Customer.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .address(address)
                .idEmployee(idEmployee)
                .build();

        Object object = customerService.createCustomer(c);
        if(object.getClass().getSimpleName().equals("String")) {
            log.error(object);
            return null;
        }

        Customer customerDB = (Customer) object;
        log.info("Customer with id: '{}' CREATED successfully", customerDB.getIdCustomer());
        return customerDB;
    }

    public Customer updateCustomer( final int idCustomer,
                                    final String name,
                                    final String phoneNumber,
                                    final String email,
                                    final String address) {

        log.info("Updating Customer with id: '{}'", idCustomer);
        Customer customer = Customer.builder()
                .idCustomer(idCustomer)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .address(address)
                .build();

        Object object = customerService.updateCustomer(customer);
        if(object.getClass().getSimpleName().equals("String")) {
            log.error(object);
            return null;
        }

        Customer customerDB = (Customer) object;
        log.info("Customer with id: '{}' UPDATED successfully", idCustomer);
        return customerDB;
    }

    public Boolean updateCustomer_employee(final int idCustomer,
                                           final int idEmployee,
                                           final boolean isRemoved) {

        log.info("Adding/Removing Customer with id: '{}' to/from Employee with id: '{}'", idCustomer, idEmployee);

        Object resp = customerService.updateCustomer_employee(idCustomer, idEmployee, isRemoved);
        if(resp.getClass().getSimpleName().equals("String")){
            log.error(resp);
            return false;
        }

        log.info("Customer with id: '{}' ADDED successfully to Employee with id: '{}'", idCustomer, idEmployee);
        return true;
    }

    public String deleteCustomer(final int idCustomer) {

        log.info("Deleting Customer with id: '{}'", idCustomer);


        log.info("Customer with id: '{}' DELETED successfully", idCustomer);
        return customerService.deleteCustomer(idCustomer);
    }
}
