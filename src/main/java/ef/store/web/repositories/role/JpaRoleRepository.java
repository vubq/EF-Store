package ef.store.web.repositories.role;

import ef.store.web.entities.RoleEntity;
import ef.store.web.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, String> {

    public Optional<RoleEntity> findByName(ERole name);

}
