package pl.sda.springfrontend.service;

import org.springframework.stereotype.Service;
import pl.sda.springfrontend.model.Role;
import pl.sda.springfrontend.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void removeAllRoles() {
        roleRepository.deleteAll();
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findFirstByRole(roleName);
    }
}
