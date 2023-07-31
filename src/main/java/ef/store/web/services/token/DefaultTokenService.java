package ef.store.web.services.token;

import ef.store.web.domains.Token;
import ef.store.web.repositories.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultTokenService implements TokenService{

    @Autowired
    private TokenRepository domainTokenRepo;

    @Override
    public Optional<Token> findByToken(String token) {
        return this.domainTokenRepo.findByToken(token);
    }

    @Override
    public Token createToken(Token token) {
        return this.domainTokenRepo.save(token);
    }

}
