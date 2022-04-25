package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.models.entities.CategoryEntity;
import net.croz.pancakes_unlimited.repositories.ReportRepository;
import net.croz.pancakes_unlimited.services.ReportService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService
{
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository)
    {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<CategoryEntity> getCategories()
    {
        return reportRepository.getCategories();
    }
}
