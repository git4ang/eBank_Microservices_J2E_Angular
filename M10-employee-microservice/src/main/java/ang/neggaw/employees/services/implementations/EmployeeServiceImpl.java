package ang.neggaw.employees.services.implementations;

import ang.neggaw.employees.entities.Employee;
import ang.neggaw.employees.services.EmployeeService;

import java.util.List;

/**
 * @author ANG
 * @since 05-08-2021 19:31
 */

public class EmployeeServiceImpl implements EmployeeService {


    @Override
    public Object createEmployee(String name, String email, int idEmployeeBoss, int idDepartment) {
        return null;
    }

    @Override
    public List<Employee> allEmployees(int offset, int count) {
        return null;
    }

    @Override
    public Employee getEmployee(int idEmployee) {
        return null;
    }

    @Override
    public Object updateEmployee(int idEmployee, String name, String email) {
        return null;
    }

    @Override
    public Object updateEmployee_employeeBoss(int idEmployee, int idEmployeeBoss, boolean isRemoved) {
        return null;
    }

    @Override
    public Object updateEmployee_department(int idEmployee, int idDepartment, boolean isRemoved) {
        return null;
    }

    @Override
    public Object updateEmployee_customer(int idCustomer, int idEmployee, boolean isRemoved) {
        return null;
    }

    @Override
    public Object updateEmployee_account(String accountNumber, int idEmployee, boolean isRemoved) {
        return null;
    }

    @Override
    public String deleteEmployee(int idEmployee) {
        return null;
    }
}
