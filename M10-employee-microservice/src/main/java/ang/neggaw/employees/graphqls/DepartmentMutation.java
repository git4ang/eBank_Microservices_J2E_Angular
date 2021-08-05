package ang.neggaw.employees.graphqls;

import ang.neggaw.employees.entities.Department;
import ang.neggaw.employees.services.DepartmentService;
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
public class DepartmentMutation implements GraphQLMutationResolver {

    private final DepartmentService departmentService;

    public Department createDepartment(final String name) {

        log.info("Creating Department with name: '{}'.", name);

        Department departmentDB = departmentService.createDepartment(name);
        if(departmentDB == null)
            log.error("Unable to create Department.");
        else {
            log.info("Department with id: '{}' CREATED successfully.", departmentDB.getIdDepartment());
            return departmentDB;
        }
        return null;
    }

    public Department updateDepartment(final int idDepartment, final String name) {

        log.info("Updating Department with id: '{}'.", idDepartment);
        Object o = departmentService.updateDepartment(idDepartment, name);
        if(o.getClass().getSimpleName().equals("String"))
            log.error(o);

        Department departmentDB = (Department) o;
        log.info("Department with id: '{}' UPDATED successfully.", idDepartment);
        return departmentDB;
    }

    public String deleteDepartment(int idDepartment) {

        log.info("Deleting Department with id: '{}'", idDepartment);

        String resp = departmentService.deleteDepartment(idDepartment);
        if (! resp.equals("deleted")) {
            log.error(resp);
            return resp;
        }

        log.info("Department with id: '{}' DELETED successfully", idDepartment);
        return String.format("Department with id: '%s' DELETED successfully", idDepartment);
    }
}