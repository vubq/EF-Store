package ef.store.web.repositories.user;

import ef.store.web.domains.User;
import ef.store.web.entities.RoleEntity;
import ef.store.web.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DefaultUserRepository implements UserRepository {

    @Autowired
    private JpaUserRepository jpaUserRepo;

    @Override
    @Transactional
    public Set<User> findAll() {
        return this.jpaUserRepo.findAll().stream().map(UserEntity::toDomain).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Optional<User> findById(String id) {
        return this.jpaUserRepo.findById(id).map(UserEntity::toDomain);
    }

    @Override
    @Transactional
    public User save(User domain) {
        return this.jpaUserRepo.saveAndFlush(UserEntity.toEntity(domain)).toDomain();
    }

    @Override
    @Transactional
    public Set<User> saveAll(List<User> domains) {
        return this.jpaUserRepo.saveAll(domains.stream().map(UserEntity::toEntity).collect(Collectors.toSet()))
                .stream()
                .map(UserEntity::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void delete(String id) {
        this.jpaUserRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        return this.jpaUserRepo.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    @Transactional
    public Optional<User> findByUserNameOrEmail(String userNameOrEmail) {
        return this.jpaUserRepo.findByUserNameOrEmail(userNameOrEmail).map(UserEntity::toDomain);
    }

    @Override
    @Transactional
    public Boolean existsByUsername(String username) {
        return this.jpaUserRepo.existsByUsername(username);
    }

    @Override
    @Transactional
    public Boolean existsByEmail(String email) {
        return this.jpaUserRepo.existsByEmail(email);
    }

}
