package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.services.PancakeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pancakes")
public class PancakeController
{
    private final PancakeService pancakeService;

    public PancakeController(PancakeService pancakeService)
    {
        this.pancakeService = pancakeService;
    }

    @GetMapping
    public List<PancakeDTO> findAll()
    {
        return pancakeService.findAll();
    }

    @GetMapping("/{id}")
    public PancakeDTO findById(@PathVariable Integer id)
    {
        return pancakeService.findById(id);
    }

    @PostMapping
    public PancakeDTO insert(@RequestBody PancakeRequest pancakeRequest)
    {
        return pancakeService.insert(pancakeRequest);
    }
}
