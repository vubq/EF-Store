package ef.store.web.repositories.user;

import ef.store.web.domains.User;
import ef.store.web.entities.RoleEntity;
import ef.store.web.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultUserRepository implements UserRepository {

    @Autowired
    private JpaUserRepository jpaUserRepo;

    @Override
    @Transactional
    public List<User> findAll() {
        return this.jpaUserRepo.findAll().stream().map(UserEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<User> findById(String id) {
        return this.jpaUserRepo.findById(id).map(UserEntity::toDomain);
    }

    @Override
    @Transactional
    public User save(User domain) {
        UserEntity user = UserEntity.toEntity(domain);
        user.setListRole(domain.getListRole().isEmpty() ? null : domain.getListRole().stream().map(RoleEntity::toEntity).collect(Collectors.toList()));
        return this.jpaUserRepo.saveAndFlush(user).toDomain();
    }

    @Override
    @Transactional
    public List<User> saveAll(List<User> domains) {
        return this.jpaUserRepo.saveAll(domains.stream().map(UserEntity::toEntity).collect(Collectors.toList())).stream().map(UserEntity::toDomain).collect(Collectors.toList());
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
    public Optional<User> findByUserNameOrEmail(String userNameOrEmail) {
        return this.jpaUserRepo.findByUserNameOrEmail(userNameOrEmail).map(UserEntity::toDomain);
    }

}
