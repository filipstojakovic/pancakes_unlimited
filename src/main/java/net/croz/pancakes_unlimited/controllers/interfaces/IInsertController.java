package net.croz.pancakes_unlimited.controllers.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface IInsertController<REQ, RESP>
{
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    RESP insert(@Valid @RequestBody REQ request);
}
