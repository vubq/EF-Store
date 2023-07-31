package ef.store.web.repositories.user;

import ef.store.web.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {

    public Optional<UserEntity> findByEmail(String email);

}
