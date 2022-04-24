package net.croz.pancakes_unlimited.security.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class AuthorizationRules
{
    private List<Rule> rules;
}
