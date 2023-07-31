package ef.store.web.repositories.token;

import ef.store.web.domains.Token;
import ef.store.web.repositories.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends BaseRepository<Token> {

    public List<Token> findAllValidTokenByUser(String id);

    public Optional<Token> findByToken(String token);

}
