package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;

import java.util.List;

public interface PancakeService
{
    List<PancakeDTO> findAll();

    PancakeDTO findById(Integer id);
    PancakeDTO insert(PancakeRequest pancakeRequest);
}
