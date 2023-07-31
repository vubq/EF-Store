package ef.store.web.repositories.role;

import ef.store.web.domains.Role;
import ef.store.web.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DefaultRoleRepository implements RoleRepository{

    @Autowired
    private JpaRoleRepository jpaRoleRepo;

    @Override
    @Transactional
    public List<Role> findAll() {
        return this.jpaRoleRepo.findAll().stream().map(RoleEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<Role> findById(String id) {
        return this.jpaRoleRepo.findById(id).map(RoleEntity::toDomain);
    }

    @Override
    @Transactional
    public Role save(Role domain) {
        return this.jpaRoleRepo.saveAndFlush(RoleEntity.toEntity(domain)).toDomain();
    }

    @Override
    @Transactional
    public List<Role> saveAll(List<Role> domains) {
        return this.jpaRoleRepo.saveAll(domains.stream().map(RoleEntity::toEntity).collect(Collectors.toList())).stream().map(RoleEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(String id) {
        this.jpaRoleRepo.deleteById(id);
    }

}
