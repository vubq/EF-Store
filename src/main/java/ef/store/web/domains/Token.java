package ef.store.web.domains;

import ef.store.web.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private String id;

    private String token;

    private TokenType tokenType;

    private boolean revoked;

    private boolean expired;

    private String userId;

}
