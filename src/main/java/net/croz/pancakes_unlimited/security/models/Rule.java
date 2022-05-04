package net.croz.pancakes_unlimited.security.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
/*
 * "methods" array of method types (such as GET, POST, PUT, DELETE,...). If left empty then allow method types are allowed.
 * "pattern" end-point that we want to secure
 * "role" array of user roles that will have access
 */
public class Rule
{
    private List<String> methods;
    private String pattern; // end-point/antPattern
    private List<String> roles;
}
