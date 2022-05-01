package net.croz.pancakes_unlimited.services.interfaces;

public interface ICrudService<ID, REQ, RESP> extends IFindService<ID, RESP>, IInsertService<REQ, RESP>
{
    RESP update(ID id, REQ request);

    void delete(ID id);
}