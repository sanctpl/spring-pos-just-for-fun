package pl.sda.springfrontend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.sda.springfrontend.handlers.CustomAuthSuccessHandler;
import pl.sda.springfrontend.handlers.CustomLogoutSuccessHandler;


@Configuration
@EnableOAuth2Client
//@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    private final
    PasswordEncoder passwordEncoder;

    private final
    UserDetailsService userDetailsService;

    private final
    CustomAuthSuccessHandler customAuthSuccessHandler;

    private final
    CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final
    SocialLoginFilter socialLoginFilter;

    @Autowired
    public SpringSecurity(PasswordEncoder passwordEncoder,
                          UserDetailsService userDetailsService,
                          CustomAuthSuccessHandler customAuthSuccessHandler,
                          CustomLogoutSuccessHandler customLogoutSuccessHandler,
                          SocialLoginFilter socialLoginFilter) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.customAuthSuccessHandler = customAuthSuccessHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.socialLoginFilter = socialLoginFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/addpost").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**", "/webjars/**", "/error**").permitAll()
                .and().formLogin().loginPage("/login").loginProcessingUrl("/login").successHandler(customAuthSuccessHandler).passwordParameter("pass").successForwardUrl("/")
                .and().logout().logoutSuccessHandler(customLogoutSuccessHandler).permitAll()
                .and().csrf().disable()
                .addFilterAt(socialLoginFilter.authFilter(), BasicAuthenticationFilter.class);



    }
}

