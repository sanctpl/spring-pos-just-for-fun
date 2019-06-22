package pl.sda.springfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.sda.springfrontend.helpers.MailingService;
import pl.sda.springfrontend.service.UserService;

@Controller
public class UserRegistrationController {
    private final
    UserService userService;
    private final MailingService mailingService;

    public UserRegistrationController(MailingService mailingService, UserService userService) {
        this.mailingService = mailingService;
        this.userService = userService;
    }

    @GetMapping("/activate/{token}")
    public String activation(@PathVariable String token) {
        userService.activateUser(token);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
