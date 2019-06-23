package pl.sda.springfrontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.springfrontend.model.User;
import pl.sda.springfrontend.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adduser")
    public String adduser(Model model, HttpSession session, HttpServletResponse sss) {
        User user = getUserFromSession(session);
        model.addAttribute("user", user);
        if (user.getId().equals(-1L)) {
            model.addAttribute("newuser", new User());
            return "singup";
        }
        return "redirect:/";
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute(name = "newuser") @Valid User newuser, BindingResult result, Model model, HttpSession session) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            return "singup";
        }
        userService.addUser(newuser);
        User user = getUserFromSession(session);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    private User getUserFromSession(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setId(-1L);
            user.setEmail("Niezalogowany");
        }
        return user;
    }

    @GetMapping("/log")
    public String loginMapping(Authentication authentication, Principal principal) {
        if (authentication != null) { //|| principal!=null) {
            System.out.println();
            authentication.getAuthorities().stream().forEach(System.out::println);
            System.out.println();
            return "redirect:/";

        }

        return "login";
    }



/*

    @RequestMapping(value = "/createOrder",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FinalOrderDetail createOrder(@RequestBody CreateOrder createOrder) {

        return postCreateOrder_restTemplate(createOrder, oAuthUser).getBody();
    }


    private ResponseEntity<String> postCreateOrder_restTemplate(CreateOrder createOrder, FacebookUser oAuthUser) {

        String url_POST = "your post url goes here";

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", String.format("%s %s", oAuthUser.getTokenType(), oAuthUser.getAccessToken()));
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<String> request = new HttpEntity<String>(createOrder, headers);

        ResponseEntity<String> result = restTemplate.exchange(url_POST, HttpMethod.POST, request, String.class);
        System.out.println("#### post response = " + result);

        return result;
    }

*/

}

