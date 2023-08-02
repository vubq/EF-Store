package ef.store.web.domains;

import ef.store.web.entities.RoleEntity;
import ef.store.web.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private EStatus status;

    private Set<Role> roles;

}
