package net.croz.pancakes_unlimited.services.impl;

import net.croz.pancakes_unlimited.PancakesUnlimitedApplication;
import net.croz.pancakes_unlimited.models.responses.IngredientReportResponse;
import net.croz.pancakes_unlimited.repositories.ReportRepository;
import net.croz.pancakes_unlimited.services.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PancakesUnlimitedApplication.class)
class ReportServiceImplTest
{
    @Mock
    private ReportRepository reportRepository;

    private ReportService serviceUnderTest;

    @BeforeEach
    public void setUp()
    {
        serviceUnderTest = new ReportServiceImpl(reportRepository);
    }

    @Test
    void findAllMostOrderedIngredientsLast30Days()
    {
        var ingredient1 = new IngredientReportResponse(1, "americka palacinka", true, new BigInteger("3"));
        var ingredient2 = new IngredientReportResponse(2, "nutela", false, new BigInteger("2"));

        var ingredientObjects1 = new Object[]{1, "americka palacinka", true, new BigInteger("3")};
        var ingredientObjects2 = new Object[]{2, "nutela", false, new BigInteger("2")};

        List<Object[]> ingredientsObjectsList = List.of(ingredientObjects1, ingredientObjects2);

        when(reportRepository.getMostOrderedIngredientsLast30Days()).thenReturn(ingredientsObjectsList);

        var result = serviceUnderTest.findAllMostOrderedIngredientsLast30Days();

        assertThat(result).isEqualTo(List.of(ingredient1, ingredient2));
    }

    @Test
    void findAllMostHealthyOrderedIngredientsLast30Days()
    {
        var ingredient1 = new IngredientReportResponse(1, "americka palacinka", true, new BigInteger("3"));
        var ingredient2 = new IngredientReportResponse(2, "nutela", true, new BigInteger("2"));

        var ingredientObjects1 = new Object[]{1, "americka palacinka", true, new BigInteger("3")};
        var ingredientObjects2 = new Object[]{2, "nutela", true, new BigInteger("2")};

        List<Object[]> ingredientsObjectsList = List.of(ingredientObjects1, ingredientObjects2);

        when(reportRepository.getMostOrderedIngredientsLast30Days()).thenReturn(ingredientsObjectsList);

        var result = serviceUnderTest.findAllMostOrderedIngredientsLast30Days();
        boolean hasUnhealthyIngredient = result.stream().anyMatch(x-> !x.getIsHealthy());

        assertThat(hasUnhealthyIngredient).isEqualTo(false);

        assertThat(result).isEqualTo(List.of(ingredient1, ingredient2));
    }
}