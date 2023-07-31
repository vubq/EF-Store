package ef.store.web.entities;

import ef.store.web.domains.User;
import ef.store.web.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eclipse.persistence.annotations.BatchFetch;
import org.eclipse.persistence.annotations.BatchFetchType;
import org.eclipse.persistence.annotations.UuidGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator(name = "uuid")
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> listRole;

    @BatchFetch(value = BatchFetchType.IN)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TokenEntity> listToken;

    public User toDomain() {
        return User.builder()
                .id(this.id)
                .userName(this.userName)
                .password(this.password)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .status(this.status)
                .listRole(this.listRole.stream().map(RoleEntity::toDomain).collect(Collectors.toList()))
                .listToken(this.listToken.stream().map(TokenEntity::toDomain).collect(Collectors.toList()))
                .build();
    }

    public static UserEntity toEntity(User domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .userName(domain.getUserName())
                .password(domain.getPassword())
                .email(domain.getEmail())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .phoneNumber(domain.getPhoneNumber())
                .address(domain.getAddress())
                .status(domain.getStatus())
//                .listRole(this.listRole.stream().map(RoleEntity::toDomain).collect(Collectors.toList()))
//                .listToken(this.listToken.stream().map(TokenEntity::toDomain).collect(Collectors.toList()))
                .build();
    }

}
