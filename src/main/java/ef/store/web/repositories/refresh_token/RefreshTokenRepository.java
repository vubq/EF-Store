package ef.store.web.repositories.refresh_token;

import ef.store.web.domains.RefreshToken;
import ef.store.web.domains.User;
import ef.store.web.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken> {

    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(String userId);

}
