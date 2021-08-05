package ang.neggaw.employees.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ANG
 * @since 05-08-2021 19:28
 */

@Entity
@Table(name = "M10_departments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDepartment;

    private String name;

    private EntityState entityState;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "employees", "department" })
    private Collection<Employee> employees = new ArrayList<>();

    public enum EntityState { CREATED, UPDATED, DELETED, PROCESSING }
}
