package ang.neggaw.entities;

import ang.neggaw.enums.EntityState;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author by: ANG
 * since: 18/04/2022 18:05
 */

@Document(collection = "roles_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleReact implements Serializable {

    @Id
    @Indexed(unique = true)
    @MongoId(FieldType.INT64)
    private String idRole;

    @NotNull
    @NotEmpty
    @Indexed(unique = true)
    private String roleName;

    private List<String> usernames = new ArrayList<>();

    private EntityState entityState;
}

