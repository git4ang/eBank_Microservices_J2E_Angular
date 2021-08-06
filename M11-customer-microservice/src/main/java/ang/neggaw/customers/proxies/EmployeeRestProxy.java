package ang.neggaw.customers.proxies;

import ang.neggaw.customers.proxyModels.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ANG
 * @since 06-08-2021 15:05
 */

@FeignClient(name = "employee-microservice", url = "http://localhost:8100")
@RequestMapping(value = "/api/employees")
public interface EmployeeRestProxy {

    @GetMapping(value = "/{idEmployee}")
    Employee getEmployeeById(@PathVariable(value = "idEmployee") final int  idEmployee);

    @PutMapping(value = "/{idCustomer}/{idEmployee}/{isRemoved}")
    Boolean updateEmployee_customer(@PathVariable(value = "idCustomer") int idCustomer,
                                    @PathVariable(value = "idEmployee") int idEmployee,
                                    @PathVariable(value = "isRemoved") boolean isRemoved);
}

