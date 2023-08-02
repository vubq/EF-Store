package ef.store.web.repositories.refresh_token;

import ef.store.web.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Query(value = "delete from RefreshTokenEntity rft where rft.user.id = :userId")
    int deleteByUser(String userId);

}
