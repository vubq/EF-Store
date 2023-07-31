package ef.store.web.services.user;

import ef.store.web.domains.User;
import ef.store.web.services.BaseService;

import java.util.Optional;

public interface UserService extends BaseService<User> {

    public Optional<User> findByUserNameOrEmail(String userNameOrEmail);

    public User createNewAccount(User user);

}
