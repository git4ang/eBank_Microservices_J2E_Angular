package ang.neggaw.employees.restControllers;

import ang.neggaw.employees.entities.Employee;
import ang.neggaw.employees.graphqls.EmployeeMutation;
import ang.neggaw.employees.graphqls.EmployeeQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 * @author ANG
 * @since 06-08-2021 15:23
 */

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeRestController {

    private final EmployeeQuery employeeQuery;
    private final EmployeeMutation employeeMutation;

    @GetMapping(value = "/{idEmployee}")
    public Employee getEmployeeById(@PathVariable(value = "idEmployee") final int  idEmployee) {
        return employeeQuery.getEmployee(idEmployee);
    }

    @PutMapping(value = "/{idCustomer}/{idEmployee}/{isRemoved}")
    Boolean updateEmployee_customer(@PathVariable(value = "idCustomer") int idCustomer,
                                    @PathVariable(value = "idEmployee") int idEmployee,
                                    @PathVariable(value = "isRemoved") boolean isRemoved) {
        return employeeMutation.updateEmployee_customer(idCustomer, idEmployee, isRemoved);
    }
}
