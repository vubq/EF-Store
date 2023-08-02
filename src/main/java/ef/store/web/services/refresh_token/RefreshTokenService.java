package ef.store.web.services.refresh_token;

import ef.store.web.domains.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(String userId);

    public RefreshToken verifyExpiration(RefreshToken token);

    public int deleteByUserId(String userId);

}
