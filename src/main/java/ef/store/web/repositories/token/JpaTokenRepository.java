package ef.store.web.repositories.token;

import ef.store.web.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaTokenRepository extends JpaRepository<TokenEntity, String> {

    @Query(value = "select t from TokenEntity t inner join UserEntity u on t.user.id = u.id where u.id = :id and (t.expired = false or t.revoked = false)")
    public List<TokenEntity> findAllValidTokenByUser(String id);

    public Optional<TokenEntity> findByToken(String token);

}
