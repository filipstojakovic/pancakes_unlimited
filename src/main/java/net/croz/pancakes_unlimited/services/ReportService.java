package net.croz.pancakes_unlimited.services;

import net.croz.pancakes_unlimited.models.responses.IngredientReportResponse;

import java.util.List;

public interface ReportService
{
    List<IngredientReportResponse> getMostOrderedIngredientsLast30Days();
    List<IngredientReportResponse> getMostHealthyOrderedIngredientsLast30Days();
}
