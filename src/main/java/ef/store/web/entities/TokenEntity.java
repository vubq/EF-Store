package ef.store.web.entities;

import ef.store.web.domains.Token;
import ef.store.web.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eclipse.persistence.annotations.BatchFetch;
import org.eclipse.persistence.annotations.BatchFetchType;
import org.eclipse.persistence.annotations.UuidGenerator;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class TokenEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator(name = "uuid")
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    @Basic(optional = false)
    @Column(name = "token", unique = true)
    private String token;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType = TokenType.BEARER;

    @Basic(optional = false)
    @Column(name = "revoked")
    private boolean revoked;

    @Basic(optional = false)
    @Column(name = "expired")
    private boolean expired;

    @BatchFetch(value = BatchFetchType.IN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Token toDomain() {
        return Token.builder()
                .id(this.id)
                .token(this.token)
                .tokenType(this.tokenType)
                .revoked(this.revoked)
                .expired(this.expired)
                .build();
    }

    public static TokenEntity toEntity(Token domain) {
        return TokenEntity.builder()
                .id(domain.getId())
                .token(domain.getToken())
                .tokenType(domain.getTokenType())
                .revoked(domain.isRevoked())
                .expired(domain.isRevoked())
                .user(domain.getUserId().isEmpty() ? null : UserEntity.builder().id(domain.getUserId()).build())
                .build();
    }

}
