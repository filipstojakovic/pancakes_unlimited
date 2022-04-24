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
        //        PasswordEncoder passwordEncoder = passwordEncoder();
        List<User> users = authorizedUsers.getUsers();

        auth.inMemoryAuthentication()
                .withUser("demo1").password("{noop}test1").roles("USER");


//        auth.inMemoryAuthentication().withUser("employee").password("{noop}palacinke").roles("employee");
        //        var inMemoryAuth = auth.inMemoryAuthentication();
        //        users.forEach(user ->
        //            {
        //                //                String encodedPassword = passwordEncoder.encode(user.getPassword());
        //                inMemoryAuth.withUser(user.getUsername()).password(user.getPassword()).roles(user.getRole().name()); //.password("{noop}secret") If, for any reason, we don't want to encode the configured password
        //            });
    }

    //    setting up Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
//          http = http.cors().and().csrf().disable();//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
//        http.authorizeRequests()
//                .antMatchers("/categories").hasRole("employee");

//        http.authorizeRequests()
//                .antMatchers("/categories").hasRole("USER")
//                .antMatchers("/").permitAll()
//                .and().httpBasic();

        http.httpBasic().and().authorizeRequests()
                .antMatchers("/categories").hasRole("USER")
                .and().csrf().disable();
//                .formLogin();

//        createAuthorizationRules(http);
        //        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //used in Authorization configure
    private void createAuthorizationRules(HttpSecurity http) throws Exception
    {
        //TODO: descrabe rules defined in json
        AuthorizationRules authorizationRules = JsonParserUtil.getObjectFromJson("rules.json", AuthorizationRules.class);
        var interceptor = http.authorizeRequests();
        //maybe add some default to permit all
        for (Rule rule : authorizationRules.getRules())
        {
            var roles = rule.getRoles().toArray(String[]::new);
            if (rule.getMethods().isEmpty())
            {
                interceptor = interceptor
                        .antMatchers(rule.getPattern())
                        .hasAnyAuthority(roles);
            } else for (String method : rule.getMethods())
            {
                interceptor = interceptor
                        .antMatchers(HttpMethod.resolve(method), rule.getPattern())
                        .hasAnyAuthority(roles);
            }
        }
        interceptor.anyRequest().denyAll();//.and();
    }

//    @Bean
//    public CorsFilter corsFilter()
//    {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.addAllowedMethod("PATCH");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//
//    @Bean
//    GrantedAuthorityDefaults grantedAuthorityDefaults()
//    {
//        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
//    }

    //    @Bean
    //    public PasswordEncoder passwordEncoder()
    //    {
    //        return new BCryptPasswordEncoder();
    //    }
}