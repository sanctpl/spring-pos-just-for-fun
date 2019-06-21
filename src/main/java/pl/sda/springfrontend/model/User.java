package pl.sda.springfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Email(message = "invalid email")
    private String email;
    private String password;
    private LocalDateTime register_date = LocalDateTime.now();
    private boolean activity = false;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    private String activateToken;
    public void addRole (Role role){
        this.roles.add(role);
    }

    public void setUsername(String displayName) {
    }
}
