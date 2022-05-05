package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.models.responses.IngredientReportResponse;
import net.croz.pancakes_unlimited.repositories.ReportRepository;
import net.croz.pancakes_unlimited.services.ReportService;
import net.croz.pancakes_unlimited.utils.MapUtils;
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
    public List<IngredientReportResponse> findAllMostOrderedIngredientsLast30Days()
    {
        List<Object[]> sqlReportResult = reportRepository.getMostOrderedIngredientsLast30Days();
        return MapUtils.mapToIngredientReport(sqlReportResult);
    }

    @Override
    public List<IngredientReportResponse> findAllMostHealthyOrderedIngredientsLast30Days()
    {
        List<Object[]> sqlReportResult = reportRepository.getMostHealthyOrderedIngredientsLast30Days();
        return MapUtils.mapToIngredientReport(sqlReportResult);
    }
}
