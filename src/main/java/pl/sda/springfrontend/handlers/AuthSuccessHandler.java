package pl.sda.springfrontend.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.sda.springfrontend.model.User;
import pl.sda.springfrontend.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private final
    UserRepository repository;

    @Autowired
    public AuthSuccessHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        User user = repository.findByEmail(authentication.getName());
        session.setAttribute("user", user);
        httpServletResponse.sendRedirect("/");
    }
}