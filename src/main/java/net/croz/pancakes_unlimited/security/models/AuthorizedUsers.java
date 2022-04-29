package net.croz.pancakes_unlimited.security.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.croz.pancakes_unlimited.models.User;

import java.util.List;


/**
 * Used while reading user.json with ObjectMapper().readValue()
 */
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class AuthorizedUsers
{
    private List<User> users;
}
