package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.PancakeEntity;
import net.croz.pancakes_unlimited.repositories.PancakeEntityRepository;
import net.croz.pancakes_unlimited.services.PancakeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PancakeServiceImpl implements PancakeService
{
    private final PancakeEntityRepository pancakeRepository;

    public PancakeServiceImpl(PancakeEntityRepository pancakeRepository)
    {
        this.pancakeRepository = pancakeRepository;
    }

    @Override
    public List<PancakeEntity> findAll()
    {
        return pancakeRepository.findAll();
    }

    @Override
    public PancakeEntity findById(Integer id)
    {
        return pancakeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public PancakeEntity insert(PancakeEntity pancake)
    {
        //TODO: check for base and fil
        return pancakeRepository.saveAndFlush(pancake);
    }
}
