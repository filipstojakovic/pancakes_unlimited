package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.services.PancakeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PancakeDTO insert(@RequestBody PancakeRequest pancakeRequest)
    {
        return pancakeService.insert(pancakeRequest);
    }

    @PutMapping("/{id}")
    public PancakeDTO update(@PathVariable Integer id, @Valid @RequestBody PancakeRequest ingredientDTO)
    {
        return pancakeService.update(id, ingredientDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        pancakeService.delete(id);
    }
}
