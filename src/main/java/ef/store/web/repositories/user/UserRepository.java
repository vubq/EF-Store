package ef.store.web.repositories.user;

import ef.store.web.domains.User;
import ef.store.web.repositories.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUserNameOrEmail(String userNameOrEmail);

}