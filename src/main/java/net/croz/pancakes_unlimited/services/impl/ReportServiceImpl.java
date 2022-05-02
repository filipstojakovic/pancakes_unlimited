package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.models.responses.IngredientReportResponse;
import net.croz.pancakes_unlimited.repositories.ReportRepository;
import net.croz.pancakes_unlimited.services.ReportService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService
{
    private static final int ID_POSITION = 0;
    private static final int NAME_POSITION = 1;
    private static final int IS_HEALTHY_POSITION = 2;
    private static final int ORDERED_TIMES_POSITION = 3;
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository)
    {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<IngredientReportResponse> getMostOrderedIngredientsLast30Days()
    {
        List<Object[]> sqlReportResult = reportRepository.getMostOrderedIngredientsLast30Days();
        return mapToIngredientReport(sqlReportResult);
    }

    @Override
    public List<IngredientReportResponse> getMostHealthyOrderedIngredientsLast30Days()
    {
        List<Object[]> sqlReportResult = reportRepository.getMostHealthyOrderedIngredientsLast30Days();
        return mapToIngredientReport(sqlReportResult);
    }


    /**
     * Mapping (converting) sql result list of object array into list of IngredientReportResponse
     *
     * @param sqlReportResult list of object arrays that are result of sql query
     * @return nicely packed result
     */
    public List<IngredientReportResponse> mapToIngredientReport(List<Object[]> sqlReportResult)
    {
        return sqlReportResult.stream().map(result ->
            {
                IngredientReportResponse response = new IngredientReportResponse();
                response.setIngredientId((Integer) result[ID_POSITION]);
                response.setName((String) result[NAME_POSITION]);
                response.setIsHealthy((Boolean) result[IS_HEALTHY_POSITION]);
                response.setOrderedTimes(((BigDecimal) result[ORDERED_TIMES_POSITION]).toBigInteger());
                return response;
            }).collect(Collectors.toList());
    }
}
