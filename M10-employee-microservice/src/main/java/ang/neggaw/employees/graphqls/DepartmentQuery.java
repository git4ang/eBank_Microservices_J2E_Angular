package ang.neggaw.employees.graphqls;

import ang.neggaw.employees.entities.Department;
import ang.neggaw.employees.services.DepartmentService;
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
public class DepartmentQuery implements GraphQLQueryResolver {

    private final DepartmentService departmentService;

    public Department getDepartment(final int idDepartment) {

        log.info("Fetching Department with id: '{}'.", idDepartment);
        if(departmentService.getDepartment(idDepartment) == null) {
            log.info("Unable to find Department with id: '{}'. Not Found.", idDepartment);
        }
        return departmentService.getDepartment(idDepartment);
    }

    public List<Department> departments(final int offset,
                                        final int count) {

        log.info("Fetching all departments ...");
        return departmentService.allDepartments(offset, count);
    }
}
