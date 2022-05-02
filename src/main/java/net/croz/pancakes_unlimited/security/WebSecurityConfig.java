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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * Setting up Authentication by using users.json file.
     * File users.json is a json file containing array of users with following attributes:
     * "username" user's username
     * "password" encoded password
     * "role" user's role (CUSTOMER, EMPLOYEE, STORE_OWNER)
     *
     * @param auth used to set in memory authentication and to add authorized users
     * @throws Exception  if file users.json is not found
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        AuthorizedUsers authorizedUsers = JsonParserUtil.getObjectFromJson("users.json", AuthorizedUsers.class);
        List<User> users = authorizedUsers.getUsers();

        var inMemoryAuth = auth.inMemoryAuthentication();
        users.forEach(user ->
            {
//                String encodedPassword = passwordEncoder.encode(user.getPassword());
                inMemoryAuth
                        .withUser(user.getUsername())
                        .password(user.getPassword()) //.password("{noop}secret") If, for any reason, we don't want to encode the configured password
                        .roles(user.getRole().name());
            });
    }

    /**
     * Setting up authorization by using rules.json file and denying all other requests.
     * File rules.json is a json file containing authorization rules for each end-point.
     * Each json object in file contains following attributes:
     * "methods" array of method types (such as GET, POST, PUT, DELETE,...). If left empty then allow method types are allowed.
     * "pattern" end-point that we want to secure
     * "role" array of user roles that will have access
     *
     * @param http used for setting authorization
     * @throws Exception if file rules.json is not found
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //TODO: describe rules defined in json
        AuthorizationRules authorizationRules = JsonParserUtil.getObjectFromJson("rules.json", AuthorizationRules.class);
        var interceptor = http.httpBasic().and().authorizeRequests();
        interceptor = interceptor.antMatchers(HttpMethod.GET, SWAGGER_ANT_PATTERN).permitAll();

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

    private static final String[] SWAGGER_ANT_PATTERN = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
}