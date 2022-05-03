package net.croz.pancakes_unlimited.controllers.crudinterfaces;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ICrudController<ID, REQ, RESP> extends IFindController<ID, RESP>, IInsertController<REQ, RESP>
{
    @PutMapping("/{id}")
    RESP update(@PathVariable ID id, @Valid @RequestBody REQ request);

    @DeleteMapping("/{id}")
    void delete(@PathVariable ID id);
}
