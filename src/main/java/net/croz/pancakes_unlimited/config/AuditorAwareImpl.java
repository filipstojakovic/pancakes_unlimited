package net.croz.pancakes_unlimited.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String>
{
    /**
     * Only get username from user who made request.
     * This username will be used for storing created_by and updated_by in database
     * @return user's username
     */
    @Override
    public Optional<String> getCurrentAuditor()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        User user = (User) authentication.getPrincipal();
        return Optional.of(user.getUsername());
    }
}