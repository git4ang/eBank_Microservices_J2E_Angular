package ang.neggaw.employees.graphqls;

import ang.neggaw.employees.entities.Employee;
import ang.neggaw.employees.services.EmployeeService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ANG
 * @since 05-08-2021 20:06
 */

@Component
@Log4j2
@RequiredArgsConstructor
public class EmployeeQuery implements GraphQLQueryResolver {

    private final EmployeeService employeeService;

    public Employee getEmployee(final int idEmployee) {

        log.info("Fetching Employee with id: '{}'.", idEmployee);
        if(employeeService.getEmployee(idEmployee) == null)
            log.error("Unable to find Employee with id: '{}'. Not Found.", idEmployee);
        return employeeService.getEmployee(idEmployee);
    }

    public List<Employee> employees(final int offset,
                                    final int count) {

        log.info("Fetching all employees ...");
        return employeeService.allEmployees(offset, count);
    }
}
