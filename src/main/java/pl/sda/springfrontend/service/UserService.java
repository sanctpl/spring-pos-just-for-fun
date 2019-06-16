package pl.sda.springfrontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.springfrontend.helpers.MailingService;
import pl.sda.springfrontend.model.User;
import pl.sda.springfrontend.repository.RoleRepository;
import pl.sda.springfrontend.repository.UserRepository;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final MailingService mailingService;
    private final
    PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, MailingService mailingService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mailingService = mailingService;
        this.passwordEncoder = passwordEncoder;
    }

    public void removeAllUsers() {
        userRepository.deleteAll();
    }

    public void addUser(User newUser) {
        newUser.addRole(roleRepository.findFirstByRole("ROLE_USER"));
        String token = getActivationTokenForUser(newUser);
        newUser.setActivateToken(token);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        try {
            mailingService.sendSimpleMessage(newUser.getEmail(), "Activation Link for: " + newUser.getEmail(),
                    "<a href='/activate?user=" + newUser.getEmail() + "&token=" + newUser.getActivateToken() + "'>Activate</a>");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        userRepository.save(newUser);
    }

    public String getActivationTokenForUser(User user) {
        String textToTokenize = user.getId() + user.getEmail() + user.getPassword() + user.getRegister_date();
        String token = encryptThisString(textToTokenize);
        return token;
    }

    public static String encryptThisString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

/*
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
