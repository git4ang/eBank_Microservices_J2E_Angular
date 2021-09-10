package ang.neggaw.accounts.proxies;

import ang.neggaw.accounts.proxyModels.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by: ANG
 * since: 10/09/2021 09:03
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
