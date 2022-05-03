package net.croz.pancakes_unlimited.services.crudinterfaces;

import java.util.List;

public interface IFindService<ID, RESP>
{
    RESP findById(ID id);

    List<RESP> findAll();
}
