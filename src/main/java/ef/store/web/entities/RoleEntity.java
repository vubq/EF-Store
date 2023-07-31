package ef.store.web.entities;

import ef.store.web.domains.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eclipse.persistence.annotations.UuidGenerator;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator(name = "uuid")
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public Role toDomain() {
        return Role.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static RoleEntity toEntity(Role domain) {
        return RoleEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

}
