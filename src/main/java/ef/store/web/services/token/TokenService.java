package ef.store.web.services.token;

import ef.store.web.domains.Token;

import java.util.Optional;

public interface TokenService {

    public Optional<Token> findByToken(String token);

    public Token createToken(Token token);
}
