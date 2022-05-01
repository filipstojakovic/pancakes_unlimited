package net.croz.pancakes_unlimited.controllers;

import liquibase.repackaged.org.apache.commons.lang3.NotImplementedException;
import net.croz.pancakes_unlimited.controllers.interfaces.IFindController;
import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.services.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController implements IFindController<Integer, CategoryEntity>
{
    private final ReportService reportService;

    public ReportController(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @Override
    public CategoryEntity findById(Integer integer)
    {
        //TODO: not implemented
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public List<CategoryEntity> findAll()
    {
        //TODO: not implemented
        throw new NotImplementedException("Not implemented");
    }
}

