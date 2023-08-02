package ef.store.web.repositories.role;

import ef.store.web.domains.Role;
import ef.store.web.enums.ERole;
import ef.store.web.repositories.BaseRepository;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role> {

    public Optional<Role> findByName(ERole name);

}
