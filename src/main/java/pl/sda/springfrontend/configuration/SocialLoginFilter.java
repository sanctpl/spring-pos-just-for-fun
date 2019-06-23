package pl.sda.springfrontend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.web.filter.CompositeFilter;
import pl.sda.springfrontend.handlers.CustomAuthSuccessHandler;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SocialLoginFilter {
    private final
    ResourceServerProperties facebookResource;

    private final
    AuthorizationCodeResourceDetails facebook;

    private final
    ResourceServerProperties githubResource;

    private final
    AuthorizationCodeResourceDetails github;

    private final
    OAuth2ClientContext oauth2ClientContext;


    public SocialLoginFilter(ResourceServerProperties facebookResource,
                             AuthorizationCodeResourceDetails facebook,
                             ResourceServerProperties githubResource,
                             AuthorizationCodeResourceDetails github,
                             @Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext) {
        this.facebookResource = facebookResource;
        this.facebook = facebook;
        this.githubResource = githubResource;
        this.github = github;
        this.oauth2ClientContext = oauth2ClientContext;
    }

    @Autowired
    CustomAuthSuccessHandler customAuthSuccessHandler;
    public Filter authFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook, oauth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource.getUserInfoUri(), facebook.getClientId());
        tokenServices.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(tokenServices);
        facebookFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler);
        filters.add(facebookFilter);

        OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
        OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github, oauth2ClientContext);
        githubFilter.setRestTemplate(githubTemplate);
        tokenServices = new UserInfoTokenServices(githubResource.getUserInfoUri(), github.getClientId());
        tokenServices.setRestTemplate(githubTemplate);
        githubFilter.setTokenServices(tokenServices);
        filters.add(githubFilter);
        filter.setFilters(filters);

        return filter;
    }
}
