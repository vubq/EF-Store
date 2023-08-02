package ef.store.web.repositories.refresh_token;

import ef.store.web.domains.RefreshToken;
import ef.store.web.domains.User;
import ef.store.web.entities.RefreshTokenEntity;
import ef.store.web.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DefaultRefreshTokenRepository implements RefreshTokenRepository{

    @Autowired
    private JpaRefreshTokenRepository jpaRefreshTokenRepo;

    @Override
    @Transactional
    public Set<RefreshToken> findAll() {
        return this.jpaRefreshTokenRepo.findAll().stream().map(RefreshTokenEntity::toDomain).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Optional<RefreshToken> findById(String id) {
        return this.jpaRefreshTokenRepo.findById(id).map(RefreshTokenEntity::toDomain);
    }

    @Override
    @Transactional
    public RefreshToken save(RefreshToken domain) {
        return this.jpaRefreshTokenRepo.saveAndFlush(RefreshTokenEntity.toEntity(domain)).toDomain();
    }

    @Override
    @Transactional
    public Set<RefreshToken> saveAll(List<RefreshToken> domains) {
        return this.jpaRefreshTokenRepo.saveAll(
                domains.stream().map(RefreshTokenEntity::toEntity).collect(Collectors.toSet()))
                .stream()
                .map(RefreshTokenEntity::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void delete(String id) {
        this.jpaRefreshTokenRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<RefreshToken> findByToken(String token) {
        return this.jpaRefreshTokenRepo.findByToken(token).map(RefreshTokenEntity::toDomain);
    }

    @Override
    @Transactional
    public int deleteByUser(String userId) {
        return this.jpaRefreshTokenRepo.deleteByUser(userId);
    }

}
