package ang.neggaw.employees.repositories;

import ang.neggaw.employees.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author ANG
 * @since 05-08-2021 19:28
 */

@RepositoryRestResource()
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByIdEmployee(int idEmployee);
}
