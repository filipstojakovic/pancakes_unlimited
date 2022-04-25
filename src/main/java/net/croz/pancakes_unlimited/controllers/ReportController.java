package net.croz.pancakes_unlimited.controllers;

import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.services.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController
{
    private final ReportService reportService;

    public ReportController(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @GetMapping
    public List<CategoryEntity> findAll()
    {
        return reportService.getCategories();
    }
}
