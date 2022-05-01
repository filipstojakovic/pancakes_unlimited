package net.croz.pancakes_unlimited.services.interfaces;

public interface IInsertService<REQ, RESP>
{
    RESP insert(REQ request);
}