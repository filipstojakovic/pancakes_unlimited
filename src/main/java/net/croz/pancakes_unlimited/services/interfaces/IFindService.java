package net.croz.pancakes_unlimited.services.interfaces;

import java.util.List;

public interface IFindService<ID, RESP>
{
    RESP findById(ID id);

    List<RESP> findAll();
}
