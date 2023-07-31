package ef.store.web.services.user;

import ef.store.web.domains.User;
import ef.store.web.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository domainUserRepo;

    @Override
    public List<User> getAll() {
        return this.domainUserRepo.findAll();
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.empty();
    }

}
