package ef.store.web.repositories.role;

import ef.store.web.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, String> {
}
