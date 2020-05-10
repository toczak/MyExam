package pl.potoczak.myexam.repository;

import org.springframework.data.repository.CrudRepository;
import pl.potoczak.myexam.model.Role;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Iterable<Role> findAll();
    Role getRoleByName(String roleName);
}
