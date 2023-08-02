package ef.store.web.entities;

import ef.store.web.domains.RefreshToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @UuidGenerator
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Basic(optional = false)
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Basic(optional = false)
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    public RefreshToken toDomain() {
        return RefreshToken.builder()
                .id(this.id)
                .userId(this.user.getId())
                .token(this.token)
                .expiryDate(this.expiryDate)
                .build();
    }

    public static RefreshTokenEntity toEntity(RefreshToken domain) {
        return RefreshTokenEntity.builder()
                .id(domain.getId())
                .user(domain.getUserId() == null ? null : UserEntity.builder().id(domain.getUserId()).build())
                .token(domain.getToken())
                .expiryDate(domain.getExpiryDate())
                .build();
    }

}
