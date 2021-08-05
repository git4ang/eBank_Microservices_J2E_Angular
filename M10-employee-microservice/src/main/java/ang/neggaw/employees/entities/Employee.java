package ang.neggaw.employees.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ANG
 * @since 05-08-2021 19:28
 */

@Entity
@Table(name = "M10_employees")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmployee;

    private String name;

    private String email;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employeeBoss" })
    @JsonIgnore
    private Employee employeeBoss;

    @OneToMany(mappedBy = "employeeBoss", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "employeeBoss", "department","underEmployees" })
    private Collection<Employee> underEmployees = new ArrayList<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "department" })
    private Department department;

    @ElementCollection
    private List<Integer> customersIds = new ArrayList<>();

    @ElementCollection
    private List<String> accountsIds = new ArrayList<>();

    private EntityState entityState;

    public enum EntityState { CREATED, UPDATED, DELETED, PROCESSING }
}