package pl.sda.springfrontend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springfrontend.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
