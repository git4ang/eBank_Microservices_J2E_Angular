package ang.neggaw.employees.services;

import ang.neggaw.employees.entities.Employee;

import java.util.List;

/**
 * @author ANG
 * @since 05-08-2021 19:29
 */

public interface EmployeeService {
    Object createEmployee(final String name, final String email, final int idEmployeeBoss, final int idDepartment);
    List<Employee> allEmployees(final int offset, final int count);
    Employee getEmployee(final int idEmployee);
    Object updateEmployee(final int idEmployee, final String name, final String email);

    Object updateEmployee_employeeBoss(final int idEmployee, final int idEmployeeBoss, final boolean isRemoved);
    Object updateEmployee_department(final int idEmployee, final int idDepartment, final boolean isRemoved);

    Object updateEmployee_customer(final int idCustomer, final int idEmployee, final boolean isRemoved);
    Object updateEmployee_account(final String accountNumber, final int idEmployee, final boolean isRemoved);

    String deleteEmployee(final int idEmployee);
}
