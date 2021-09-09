package ang.neggaw.employees.graphqls;

import ang.neggaw.employees.entities.Employee;
import ang.neggaw.employees.services.EmployeeService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @author ANG
 * @since 05-08-2021 20:06
 */

@Component
@Log4j2
@RequiredArgsConstructor
public class EmployeeMutation implements GraphQLMutationResolver {

    private final EmployeeService employeeService;

    public Employee createEmployee(final String name,
                                   final String email,
                                   final int idEmployeeBoss,
                                   final int idDepartment) {

        log.info("Creating Employee with name: '{}'.", name);

        Object object = employeeService.createEmployee(name, email, idEmployeeBoss, idDepartment);
        if(object.getClass().getSimpleName().equals("String")) {
            log.error(object);
            return null;
        }

        Employee employeeDB = (Employee) object;
        log.info("Employee with id: '{}' CREATED successfully.", employeeDB.getIdEmployee());
        return employeeDB;
    }

    public Employee updateEmployee(final int idEmployee,
                                   final String name,
                                   final String email) {

        log.info("Updating Employee with id: '{}'.", idEmployee);
        Object o = employeeService.updateEmployee(idEmployee, name, email);
        if(o.getClass().getSimpleName().equals("String")) {
            log.error(o);
            return null;
        }


        Employee employeeDB = (Employee) o;
        log.info("Employee with id: '{}' UPDATED successfully.", idEmployee);
        return employeeDB;
    }

    public Boolean updateEmployee_employeeBoss(final int idEmployee,
                                               final int idEmployeeBoss,
                                               boolean isRemoved) {
        Object resp = employeeService.updateEmployee_employeeBoss(idEmployee, idEmployeeBoss, isRemoved);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return false;
        }

        if(isRemoved)
            log.info("Employee with id: '{}' REMOVED successfully from EmployeeBoss with id: '{}'.", idEmployee, idEmployeeBoss);
        else
            log.info("Employee with id: '{}' ADDED successfully to EmployeeBoss with id: '{}'.", idEmployee, idEmployeeBoss);
        return true;
    }

    public Boolean updateEmployee_department(final int idEmployee,
                                             final int idDepartment,
                                             boolean isRemoved) {
        Object resp = employeeService.updateEmployee_department(idEmployee, idDepartment, isRemoved);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return false;
        }

        if(isRemoved)
            log.info("Employee with id: '{}' REMOVED successfully from Department with id: '{}'.", idEmployee, idDepartment);
        else
            log.info("Employee with id: '{}' ADDED successfully to Department with id: '{}'.", idEmployee, idDepartment);
        return true;
    }

    public Boolean updateEmployee_customer(final int idCustomer,
                                           final int idEmployee,
                                           boolean isRemoved) {
        Object resp = employeeService.updateEmployee_customer(idCustomer, idEmployee, isRemoved);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return false;
        }

        if(isRemoved)
            log.info("Customer with id: '{}' REMOVED successfully from Employee with id: '{}'.", idCustomer, idEmployee);
        else
            log.info("Customer with id: '{}' ADDED successfully from Employee with id: '{}'.", idCustomer, idEmployee);
        return true;
    }

    public Boolean updateEmployee_account(final String accountNumber,
                                          final int idEmployee,
                                          boolean isRemoved) {
        Object resp = employeeService.updateEmployee_account(accountNumber, idEmployee, isRemoved);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return false;
        }

        if(isRemoved)
            log.info("Account with id: '{}' REMOVED successfully from Employee with id: '{}'.", accountNumber, idEmployee);
        else
            log.info("Account with id: '{}' ADDED successfully from Employee with id: '{}'.", accountNumber, idEmployee);
        return true;
    }

    public String deleteEmployee(int idEmployee) {

        log.info("Deleting Employee with id: '{}'", idEmployee);

        String resp = employeeService.deleteEmployee(idEmployee);
        if (resp == null) {
            log.error("Unable to delete. Employee with id: '{}' Not Found.", idEmployee);
            return String.format("Unable to delete. Employee with id: '%s' Not Found.", idEmployee);
        }

        log.info("Employee with id: '{}' DELETED successfully", idEmployee);
        return String.format("Employee with id: '%s' DELETED successfully", idEmployee);
    }
}