package ef.store.web.services.role;

import ef.store.web.domains.Role;
import ef.store.web.enums.ERole;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(ERole name);

}
