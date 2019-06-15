package pl.sda.springfrontend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import pl.sda.springfrontend.handlers.CustomAuthSuccessHandler;
import pl.sda.springfrontend.handlers.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    private final
    UserDetailsService userDetailsService;

    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    public SpringSecurity(UserDetailsService userDetailsService, CustomAuthSuccessHandler customAuthSuccessHandler, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthSuccessHandler = customAuthSuccessHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/addpost*").hasAnyAuthority("ADMIN","MOD")
                .antMatchers("/**").permitAll()
                .and().formLogin().loginPage("/login").successHandler(customAuthSuccessHandler)
                .and().logout().logoutSuccessHandler(customLogoutSuccessHandler).logoutSuccessUrl("/").permitAll()
                .and().csrf().disable();

    }
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
}

}
