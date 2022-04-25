package net.croz.pancakes_unlimited.security;

import net.croz.pancakes_unlimited.models.User;
import net.croz.pancakes_unlimited.security.models.AuthorizationRules;
import net.croz.pancakes_unlimited.security.models.AuthorizedUsers;
import net.croz.pancakes_unlimited.security.models.Rule;
import net.croz.pancakes_unlimited.utils.JsonParserUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

    //    setting up Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        AuthorizedUsers authorizedUsers = JsonParserUtil.getObjectFromJson("users.json", AuthorizedUsers.class);
        PasswordEncoder passwordEncoder = passwordEncoder();
        List<User> users = authorizedUsers.getUsers();

        var inMemoryAuth = auth.inMemoryAuthentication();
        users.forEach(user ->
            {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                inMemoryAuth
                        .withUser(user.getUsername())
                        .password(encodedPassword)  //.password("{noop}secret") If, for any reason, we don't want to encode the configured password
                        .roles(user.getRole().name());
            });
    }

    //    setting up Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //TODO: describe rules defined in json
        AuthorizationRules authorizationRules = JsonParserUtil.getObjectFromJson("rules.json", AuthorizationRules.class);
        var interceptor = http.httpBasic().and().authorizeRequests();
        for (Rule rule : authorizationRules.getRules())
        {
            var roles = rule.getRoles().toArray(String[]::new);
            if (rule.getMethods().isEmpty())
            {
                interceptor = interceptor
                        .antMatchers(rule.getPattern())
                        .hasAnyRole(roles);
            } else for (String method : rule.getMethods())
            {
                interceptor = interceptor
                        .antMatchers(HttpMethod.resolve(method), rule.getPattern())
                        .hasAnyRole(roles);
            }
        }
        interceptor.anyRequest().denyAll();
        interceptor.and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}