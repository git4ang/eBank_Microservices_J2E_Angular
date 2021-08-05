package ang.neggaw.employees.services;

import ang.neggaw.employees.entities.Department;

import java.util.List;

/**
 * @author ANG
 * @since 05-08-2021 19:29
 */

public interface DepartmentService {
    Department createDepartment(String name);
    List<Department> allDepartments(int offset, int count);
    Department getDepartment(int idDepartment);
    Object updateDepartment(int idDepartment, String name);
    String deleteDepartment(int idDepartment);
}
