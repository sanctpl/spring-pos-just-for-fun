package pl.sda.springfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AdminToolsController {
    @GetMapping("/admin")
    public String adminPage(Principal principal) {
        return "admin/tables";
    }
}
