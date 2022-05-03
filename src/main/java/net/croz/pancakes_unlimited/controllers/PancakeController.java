package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.controllers.crudinterfaces.ICrudController;
import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.services.PancakeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pancakes")
public class PancakeController implements ICrudController<Integer, PancakeRequest, PancakeDTO>
{
    private final PancakeService pancakeService;

    public PancakeController(PancakeService pancakeService)
    {
        this.pancakeService = pancakeService;
    }

    @Override
    public List<PancakeDTO> findAll()
    {
        return pancakeService.findAll();
    }

    @Override
    public PancakeDTO findById(Integer id)
    {
        return pancakeService.findById(id);
    }

    @Override
    public PancakeDTO insert(PancakeRequest pancakeRequest)
    {
        return pancakeService.insert(pancakeRequest);
    }

    @Override
    public PancakeDTO update(Integer id, PancakeRequest ingredientDTO)
    {
        return pancakeService.update(id, ingredientDTO);
    }

    @Override
    public void delete(Integer id)
    {
        pancakeService.delete(id);
    }
}
