package pl.sda.springfrontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sda.springfrontend.model.Role;
import pl.sda.springfrontend.model.User;
import pl.sda.springfrontend.service.CommentService;
import pl.sda.springfrontend.service.PostService;
import pl.sda.springfrontend.service.RoleService;
import pl.sda.springfrontend.service.UserService;

@Controller
public class InitAdminUserController {
    private final
    PasswordEncoder passwordEncoder;
    private final
    UserService userService;
    private final
    RoleService roleService;
    private final
    PostService postService;
    private final
    CommentService commentService;

    @Autowired
    public InitAdminUserController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/init")
    public String InitAdminUser() {
        commentService.removeAllComents();
        postService.removeAllPosts();
        userService.removeAllUsers();
        roleService.removeAllRoles();
        User user = new User("admin@admin.pl", passwordEncoder.encode("admin"));
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Role roleMod = new Role("ROLE_MOD");
        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);
        roleService.addRole(roleMod);
        user.setActivity(true);
        user.addRole(roleService.getRoleByName("ROLE_ADMIN"));
        userService.addUser(user);

        return "redirect:/";
    }
}
