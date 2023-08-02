package ef.store.web.repositories.user;

import ef.store.web.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {

    public Optional<UserEntity> findByEmail(String email);

    @Query(value = "select u from UserEntity u where u.username = :userNameOrEmail or u.email = :userNameOrEmail")
    public Optional<UserEntity> findByUserNameOrEmail(String userNameOrEmail);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
