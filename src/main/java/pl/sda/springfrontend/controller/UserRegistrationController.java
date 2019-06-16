package pl.sda.springfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sda.springfrontend.helpers.MailingService;

@Controller
public class UserRegistrationController {

    private final MailingService mailingService;

    public UserRegistrationController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @GetMapping("/registration")
    public String RegistrationForm() {
        mailingService.sendSimpleMessage("tomasz.morawski1987@gmail.com", "Authentication", "Give me yours password!");

        mailingService.sendSimpleMessage("damian.kolczynski@gmail.com", "Authentication", "Give me yours password!");
        return "redirect:/";
    }
}
