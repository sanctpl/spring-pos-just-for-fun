package pl.sda.springfrontend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.springfrontend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail (String email);
}
