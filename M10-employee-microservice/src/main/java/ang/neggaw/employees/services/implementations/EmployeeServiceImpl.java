package ang.neggaw.employees.services.implementations;

import ang.neggaw.employees.entities.Department;
import ang.neggaw.employees.entities.Employee;
import ang.neggaw.employees.proxies.AccountProxy;
import ang.neggaw.employees.proxies.CustomerProxy;
import ang.neggaw.employees.proxyModels.Account;
import ang.neggaw.employees.proxyModels.Customer;
import ang.neggaw.employees.repositories.DepartmentRepository;
import ang.neggaw.employees.repositories.EmployeeRepository;
import ang.neggaw.employees.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ANG
 * @since 05-08-2021 19:31
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CustomerProxy customerProxy;
    private final AccountProxy accountProxy;

    @Override
    public Object createEmployee(final String name,
                                 final String email,
                                 final int idEmployeeBoss,
                                 final int idDepartment) {

        Department department = departmentRepository.findByIdDepartment(idDepartment);
        if (department == null)
            return String.format("Unable to create. Employee with Department with id: '%s' Not Found.", idDepartment);

        Employee boss = employeeRepository.findByIdEmployee(idEmployeeBoss);
        if (boss == null)
            return String.format("Unable to create. Employee with EmployeeBoss with id: '%s' Not Found.", idEmployeeBoss);

        Employee employee = Employee.builder()
                .entityState(Employee.EntityState.CREATED)
                .name(name)
                .email(email)
                .employeeBoss(boss)
                .department(department)
                .build();

        employee.setDepartment(department);
        employee.setEmployeeBoss(boss);

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> allEmployees(int offset, int count) {

        List<Employee> employees = new ArrayList<>(employeeRepository.findAll());
        offset = offset > count ? 0 : offset;
        count = Math.min(count, employees.size());
        return employees.subList(offset, count);
    }

    @Override
    public Employee getEmployee(int idEmployee) {
        return Optional.of(employeeRepository.findById(idEmployee)).get().orElse(null);
    }

    @Override
    public Object updateEmployee(final int idEmployee,
                                 final String name,
                                 final String email) {

        Employee employeeDB = getEmployee(idEmployee);
        if( employeeDB == null)
            return String.format("Unable to update. Employee with Id '%s' Not Found.", idEmployee);

        employeeDB.setEntityState(Employee.EntityState.UPDATED);
        employeeDB.setName(name);
        employeeDB.setEmail(email);
        return employeeRepository.save(employeeDB);
    }

    @Override
    public Object updateEmployee_employeeBoss(final int idEmployee, final int idEmployeeBoss, final boolean isRemoved) {

        Employee employeeDB = getEmployee(idEmployee);
        if( employeeDB == null)
            return String.format("Unable to update. Employee with Id '%s' Not Found.", idEmployee);

        Employee boss = getEmployee(idEmployee);
        if( boss == null)
            return String.format("Unable to update. EmployeeBoss with Id '%s' Not Found.", idEmployeeBoss);

        employeeDB.setEntityState(Employee.EntityState.UPDATED);
        boss.setEntityState(Employee.EntityState.UPDATED);
        if(isRemoved)
            boss.setUnderEmployees(boss.getUnderEmployees().stream().filter(employee -> employee.equals(employeeDB)).collect(Collectors.toList()));
        else {
            boss.getUnderEmployees().add(employeeDB);
            employeeDB.setEmployeeBoss(boss);
        }
        employeeRepository.saveAndFlush(boss);

        return employeeRepository.saveAndFlush(employeeDB);
    }

    @Override
    public Object updateEmployee_department(final int idEmployee, final int idDepartment, final boolean isRemoved) {

        var employeeDB = getEmployee(idEmployee);
        if( employeeDB == null)
            return String.format("Unable to update. Employee with Id '%s' Not Found.", idEmployee);

        Department department = departmentRepository.findByIdDepartment(idDepartment);
        if (department == null)
            return String.format("Unable to update. Employee with Department with id: '%s' Not Found.", idDepartment);

        employeeDB.setEntityState(Employee.EntityState.UPDATED);
        department.setEntityState(Department.EntityState.UPDATED);
        if(isRemoved)
            department.setEmployees(department.getEmployees().stream().filter(employee -> employee.equals(employeeDB)).collect(Collectors.toList()));
        else {
            department.getEmployees().add(employeeDB);
            employeeDB.setDepartment(department);
        }
        departmentRepository.saveAndFlush(department);

        return employeeRepository.saveAndFlush(employeeDB);
    }

    @Override
    public Object updateEmployee_customer(final int idCustomer, final int idEmployee, final boolean isRemoved) {

        Employee employeeDB = getEmployee(idEmployee);
        if(employeeDB == null)
            return String.format("Unable to update (add/remove) Employee. Employee with id: '%s' Not Found.", idEmployee);
        try {
            Customer customer = customerProxy.getCustomerById(idCustomer);

            employeeDB.setEntityState(Employee.EntityState.UPDATED);
            if(isRemoved)
                employeeDB.setCustomersIds(employeeDB.getCustomersIds().stream().filter(id -> id == idCustomer).collect(Collectors.toList()));
            else
                employeeDB.setCustomersIds(Collections.singletonList(idCustomer));
        } catch (Exception e) {
            return String.format("Unable to update (add/remove) Employee. Customer with id: '%s' Not Found. Message: %s", idCustomer, e.getMessage());
        }
        return employeeRepository.saveAndFlush(employeeDB);
    }

    @Override
    public Object updateEmployee_account(final String accountNumber, final int idEmployee, final boolean isRemoved) {

        Employee employeeDB = getEmployee(idEmployee);
        if(employeeDB == null)
            return String.format("Unable to update (add/remove) Employee. Employee with id: '%s' Not Found.", idEmployee);
        try {
            Account account = accountProxy.getAccountById(accountNumber);

            employeeDB.setEntityState(Employee.EntityState.UPDATED);
            if(isRemoved)
                employeeDB.setAccountsIds(employeeDB.getAccountsIds().stream().filter(id -> id.equals(accountNumber)).collect(Collectors.toList()));
            else
                employeeDB.setAccountsIds(Collections.singletonList(accountNumber));
        } catch (Exception e) {
            return String.format("Unable to update (add/remove) Employee. Account with id: '%s' Not Found. Message: %s", accountNumber, e.getMessage());
        }
        return employeeRepository.saveAndFlush(employeeDB);
    }

    @Override
    public String deleteEmployee(final int idEmployee) {

        Employee employee = getEmployee(idEmployee);
        if(employee == null)
            return String.format("Unable to delete. Employee with id: '%s' Not Found.", idEmployee);

        employee.setEntityState(Employee.EntityState.DELETED);
        employeeRepository.delete(employee);
        return String.format("Employee with id: '%s' DELETED successfully.", idEmployee);
    }
}
