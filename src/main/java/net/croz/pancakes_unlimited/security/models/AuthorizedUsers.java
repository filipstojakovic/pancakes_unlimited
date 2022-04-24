package net.croz.pancakes_unlimited.security.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.croz.pancakes_unlimited.models.User;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Used while reading user.json with ObjectMapper().readValue()
 */
@Data
public class AuthorizedUsers
{
    private List<User> users;
}
