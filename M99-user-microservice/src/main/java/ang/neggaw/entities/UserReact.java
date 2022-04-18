package ang.neggaw.entities;

import ang.neggaw.enums.EntityState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author by: ANG
 * since: 18/04/2022 18:05
 */

@Document(collection = "users_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserReact implements Serializable {

    @Id
    @Indexed(unique = true)
    @MongoId(FieldType.INT64)
    private String idUser;

    @Indexed(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private String password;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String rePassword;

    @Indexed(unique = true)
    private String email;

    private boolean enabled;

    private Date lastPasswordResetDate;

    private List<String> authorities = new ArrayList<>();

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private String idRole;

    private EntityState entityState;
}
