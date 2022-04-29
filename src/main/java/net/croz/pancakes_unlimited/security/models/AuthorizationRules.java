package net.croz.pancakes_unlimited.security.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class AuthorizationRules
{
    private List<Rule> rules;
}
