package ef.store.web.repositories.token;

import ef.store.web.domains.Token;
import ef.store.web.entities.TokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultTokenRepository implements TokenRepository {

    @Autowired
    private JpaTokenRepository jpaTokenRepo;

    @Override
    @Transactional
    public List<Token> findAll() {
        return this.jpaTokenRepo.findAll().stream().map(TokenEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<Token> findById(String id) {
        return this.jpaTokenRepo.findById(id).map(TokenEntity::toDomain);
    }

    @Override
    @Transactional
    public Token save(Token domain) {
        return this.jpaTokenRepo.saveAndFlush(TokenEntity.toEntity(domain)).toDomain();
    }

    @Override
    @Transactional
    public List<Token> saveAll(List<Token> domains) {
        return this.jpaTokenRepo.saveAll(domains.stream().map(TokenEntity::toEntity).collect(Collectors.toList())).stream().map(TokenEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(String id) {
        this.jpaTokenRepo.deleteById(id);
    }

    @Override
    @Transactional
    public List<Token> findAllValidTokenByUser(String id) {
        return this.jpaTokenRepo.findAllValidTokenByUser(id).stream().map(TokenEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<Token> findByToken(String token) {
        return this.jpaTokenRepo.findByToken(token).map(TokenEntity::toDomain);
    }

}
