package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.entities.PancakeEntity;

import java.util.List;

public interface PancakeService
{
    List<PancakeEntity> findAll();
    PancakeEntity findById(Integer id);
    PancakeEntity insert(PancakeEntity pancake);
}
