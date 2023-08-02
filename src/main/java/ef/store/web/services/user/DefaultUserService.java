package ef.store.web.services.user;

import ef.store.web.domains.User;
import ef.store.web.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository domainUserRepo;

    @Override
    public Set<User> getAll() {
        return this.domainUserRepo.findAll();
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUserNameOrEmail(String userNameOrEmail) {
        return this.domainUserRepo.findByUserNameOrEmail(userNameOrEmail);
    }

    @Override
    public User createNewAccount(User user) {
        return this.domainUserRepo.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return this.domainUserRepo.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return this.domainUserRepo.existsByEmail(email);
    }

}
