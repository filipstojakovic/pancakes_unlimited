package net.croz.pancakes_unlimited.services.crudinterfaces;

public interface IInsertService<REQ, RESP>
{
    RESP insert(REQ request);
}