package net.croz.pancakes_unlimited.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import net.croz.pancakes_unlimited.models.responses.IngredientReportResponse;
import net.croz.pancakes_unlimited.services.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReportController.class)
class ReportControllerTest
{
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static ObjectWriter objectWriter;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportService reportService;
    @BeforeAll
    static void beforeAll()
    {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"STORE_OWNER"})
    @Test
    void findAllMostOrderedIngredientsLast30Days_validRequest_authorized_return200AndResult()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/reports/all");

        IngredientReportResponse ingredientReportResponse1 = new IngredientReportResponse(1,"nutela",true,new BigInteger("4"));
        IngredientReportResponse ingredientReportResponse2 = new IngredientReportResponse(2,"americka palacinka",false,new BigInteger("2"));

        List<IngredientReportResponse> expectedResult = List.of(ingredientReportResponse1, ingredientReportResponse2);
        when(reportService.findAllMostOrderedIngredientsLast30Days()).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<IngredientReportResponse> responseResult = Arrays.asList(mapper.readValue(responseContent, IngredientReportResponse[].class));

        assertThat(responseResult).isEqualTo(expectedResult);
    }


    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE","CUSTOMER"})
    @Test
    void findAllMostOrderedIngredientsLast30Days_validRequest_Forbidden_return403()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/reports/all");
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findAllMostOrderedIngredientsLast30Days_validRequest_unauthorized_return401()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/reports/all");
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @SneakyThrows
    @WithMockUser(username = "w", roles = {"STORE_OWNER"})
    @Test
    void findAllMostHealOrderedIngredientsLast30Days_validRequest_authorized_return200AndResult()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/reports/healthy");

        IngredientReportResponse ingredientReportResponse1 = new IngredientReportResponse(1,"nutela",true,new BigInteger("4"));
        IngredientReportResponse ingredientReportResponse2 = new IngredientReportResponse(2,"americka palacinka",true,new BigInteger("2"));

        List<IngredientReportResponse> expectedResult = List.of(ingredientReportResponse1, ingredientReportResponse2);
        when(reportService.findAllMostHealthyOrderedIngredientsLast30Days()).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<IngredientReportResponse> responseResult = Arrays.asList(mapper.readValue(responseContent, IngredientReportResponse[].class));

        assertThat(responseResult).isEqualTo(expectedResult);
    }


    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE","CUSTOMER"})
    @Test
    void findAllMostHealOrderedIngredientsLast30Days_validRequest_Forbidden_return403()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/reports/healthy");
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findAllMostHealOrderedIngredientsLast30Days_validRequest_unauthorized_return401()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/reports/healthy");
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }
}