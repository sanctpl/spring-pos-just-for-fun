package pl.sda.springfrontend.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
   /* UserRepository userRepository;
    RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User setRole(Long role_id, Long user_id)
    {
        if (userRepository.findById(user_id).isPresent() &&
            roleRepository.findById(role_id).isPresent()){
            User user = userRepository.getOne(user_id);
            Role role = roleRepository.getOne(role_id);
            user.addRole(role);
            return userRepository.save(user);
        }
        return new User();
    }

    public User saveUser(String email, String password){
        User user = new User(email,password);

        userRepository.save(user);
        setRole((long) 1,user.getId());
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User setActive(Long id) {
        if (userRepository.findById(id).isPresent()) {
           User user = userRepository.findById(id).get();
           user.setActivity(true);
           userRepository.save(user);
           return user;
        }
        return null;
    }

    public void removeUser(Long id){
        userRepository.findById(id).ifPresent(user ->
            userRepository.delete(user));
    }*/

}
