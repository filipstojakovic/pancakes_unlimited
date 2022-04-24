package net.croz.pancakes_unlimited.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.croz.pancakes_unlimited.models.enums.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
public class User
{
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
