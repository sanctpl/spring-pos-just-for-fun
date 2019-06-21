package pl.sda.springfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sda.springfrontend.helpers.MailingService;

import javax.mail.MessagingException;

@Controller
public class UserRegistrationController {

    private final MailingService mailingService;

    public UserRegistrationController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @GetMapping("/registration")
    public String RegistrationForm() {


        try {
            mailingService.sendSimpleMessage("damian.kolczynski@gmail.com", "Authentication", "Give me yours password!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
