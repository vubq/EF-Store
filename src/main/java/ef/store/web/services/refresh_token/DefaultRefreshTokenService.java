package ef.store.web.services.refresh_token;

import ef.store.web.domains.RefreshToken;
import ef.store.web.entities.RefreshTokenEntity;
import ef.store.web.entities.UserEntity;
import ef.store.web.exception.TokenRefreshException;
import ef.store.web.repositories.refresh_token.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultRefreshTokenService implements RefreshTokenService{

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository domainRefreshTokenRepo;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return this.domainRefreshTokenRepo.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .userId(userId)
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();
        return this.domainRefreshTokenRepo.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            this.domainRefreshTokenRepo.delete(token.getId());
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new sign-in request");
        }
        return token;
    }

    @Override
    public int deleteByUserId(String userId) {
        return this.domainRefreshTokenRepo.deleteByUser(userId);
    }

}
