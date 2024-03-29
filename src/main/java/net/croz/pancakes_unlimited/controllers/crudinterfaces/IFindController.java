package net.croz.pancakes_unlimited.controllers.crudinterfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IFindController<ID, RESP>
{
    @GetMapping
    List<RESP> findAll();

    @GetMapping("/{id}")
    RESP findById(@PathVariable ID id);
}
