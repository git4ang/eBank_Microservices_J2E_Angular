package ang.neggaw.employees.services.implementations;

import ang.neggaw.employees.entities.Department;
import ang.neggaw.employees.repositories.DepartmentRepository;
import ang.neggaw.employees.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ANG
 * @since 05-08-2021 19:31
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(String name) {
        Department department = Department.builder()
                .entityState(Department.EntityState.CREATED)
                .name(name)
                .build();
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> allDepartments(int offset, int count) {

        List<Department> departments = new ArrayList<>(departmentRepository.findAll());
        offset = offset > count ? 0 : offset;
        count = Math.min(count, departments.size());
        return departments.subList(offset, count);
    }

    @Override
    public Department getDepartment(int idDepartment) {
        return Optional.of(departmentRepository.findById(idDepartment)).get().orElse(null);
    }

    @Override
    public Object updateDepartment(int idDepartment, String name) {

        Department departmentDB = getDepartment(idDepartment);
        if(departmentDB == null)
            return String.format("Unable to update. Department with Id: '%s' Not Found", idDepartment);

        departmentDB.setEntityState(Department.EntityState.UPDATED);
        departmentDB.setName(name);
        return departmentRepository.save(departmentDB);
    }

    @Override
    public String deleteDepartment(int idDepartment) {
        Department department = getDepartment(idDepartment);
        if (department == null )
            return String.format("Unable to delete. Department with id: '%s' Not Found.", idDepartment);

        department.setEntityState(Department.EntityState.DELETED);
        departmentRepository.delete(department);
        return "deleted";
    }
}
