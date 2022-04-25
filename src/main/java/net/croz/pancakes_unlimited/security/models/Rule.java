package net.croz.pancakes_unlimited.security.models;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rule
{
    private List<String> methods;
    private String pattern; // end-point/antPattern
    private List<String> roles;
}
