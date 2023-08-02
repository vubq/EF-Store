package ef.store.web.services.role;

import ef.store.web.domains.Role;
import ef.store.web.enums.ERole;
import ef.store.web.repositories.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultRoleService implements RoleService{

    @Autowired
    private RoleRepository domainRoleRepo;

    @Override
    public Optional<Role> findByName(ERole name) {
        return this.domainRoleRepo.findByName(name);
    }

}
