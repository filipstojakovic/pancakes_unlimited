package net.croz.pancakes_unlimited.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.dtos.IngredientDTO;
import net.croz.pancakes_unlimited.models.requests.IngredientRequest;
import net.croz.pancakes_unlimited.services.IngredientService;
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

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = IngredientController.class)
class IngredientControllerTest
{
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static ObjectWriter objectWriter;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IngredientService ingredientService;

    @BeforeAll
    static void beforeAll()
    {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void findAll_validRequest_authorized_return200AndResult()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/ingredients");

        IngredientDTO ingredientDTO1 = new IngredientDTO(1, "americka palacinka", true, new BigDecimal("2.2"), new CategoryDTO(1, "baza"));
        IngredientDTO ingredientDTO2 = new IngredientDTO(2, "nutela", true, new BigDecimal("5.21"), new CategoryDTO(2, "fil"));

        List<IngredientDTO> expectedIngredients = List.of(ingredientDTO1, ingredientDTO2);
        when(ingredientService.findAll()).thenReturn(expectedIngredients);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<IngredientDTO> resultIngredients = Arrays.asList(mapper.readValue(responseContent, IngredientDTO[].class));

        assertThat(resultIngredients).isEqualTo(expectedIngredients);
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"CUSTOMER","STORE_OWNER"})
    @Test
    void findAll_validRequest_Forbidden_return403()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/ingredients");
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findAll_validRequest_unauthorized_return401()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/ingredients");
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void findById_validRequest_authorized_return200AndResult()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/ingredients/{id}", id);

        IngredientDTO expectedResponse = new IngredientDTO(1, "americka palacinka", true, new BigDecimal("2.2"), new CategoryDTO(1, "baza"));
        when(ingredientService.findById(id)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        IngredientDTO resultResponse = mapper.readValue(responseContent, IngredientDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void findById_invalidRequest_authorized_return404()
    {
        int id = 5;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/ingredients/{id}", id);
        when(ingredientService.findById(id)).thenThrow(new NotFoundException());

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_NOT_FOUND)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"CUSTOMER","STORE_OWNER"})
    @Test
    void findById_validRequest_Forbidden_return403()
    {
        int id = 5;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/ingredients/{id}", id);
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findById_validRequest_unauthorized_return401()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/ingredients/{id}", id);
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void insert_validRequest_authorized_return200AndResult()
    {
        IngredientRequest requestObject = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.23"));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/ingredients")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        IngredientDTO expectedResponse = new IngredientDTO(1, "nutela", true, new BigDecimal("1.23"), new CategoryDTO(1, "fil"));
        when(ingredientService.insert(requestObject))
                .thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_CREATED)).andReturn();

        verify(ingredientService).insert(requestObject);

        String responseContent = result.getResponse().getContentAsString();
        IngredientDTO resultResponse = mapper.readValue(responseContent, IngredientDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void insert_invalidRequest_authorized_return400()
    {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/ingredients")
                .contentType(APPLICATION_JSON_UTF8)
                .param("name", "nutela")
                .param("categoryName", "fil")
                .param("isHealthy", "true");
        //missing price

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_BAD_REQUEST)).andReturn();

    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"CUSTOMER","STORE_OWNER"})
    @Test
    void insert_validRequest_Forbidden_return403()
    {
        IngredientRequest requestObject = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.23"));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/ingredients")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void insert_validRequest_unauthorized_return401()
    {
        IngredientRequest requestObject = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.23"));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/ingredients")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void update_validRequest_authorized_return200AndResult()
    {
        int id = 1;
        IngredientRequest requestObject = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.23"));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/ingredients/{id}", id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        IngredientDTO expectedResponse = new IngredientDTO(id, "nutela", true, new BigDecimal("1.23"), new CategoryDTO(1, "fil"));
        when(ingredientService.update(id, requestObject))
                .thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        IngredientDTO resultResponse = mapper.readValue(responseContent, IngredientDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void update_validRequest_authorized_return404()
    {
        int id = 1;
        IngredientRequest requestObject = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.23"));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/ingredients/{id}", id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        IngredientDTO expectedResponse = new IngredientDTO(id, "nutela", true, new BigDecimal("1.23"), new CategoryDTO(1, "fil"));
        when(ingredientService.update(id, requestObject))
                .thenThrow(new NotFoundException());

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_NOT_FOUND)).andReturn();

    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void update_invalidRequest_authorized_return400()
    {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/ingredients/{id}", 1)
                .contentType(APPLICATION_JSON_UTF8)
                .param("name", "nutela")
                .param("categoryName", "fil")
                .param("isHealthy", "true");
        //missing price

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_BAD_REQUEST)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"CUSTOMER","STORE_OWNER"})
    @Test
    void update_validRequest_Forbidden_return403()
    {
        IngredientRequest requestObject = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.23"));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/ingredients/{id}", 1)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void update_validRequest_unauthorized_return401()
    {
        IngredientRequest requestObject = new IngredientRequest("nutela", "fil", true, new BigDecimal("1.23"));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/ingredients/{id}", 1)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void delete_validRequest_authorized_return200()
    {
        int id=1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/ingredients/{id}", id);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();
        verify(ingredientService).delete(id);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void delete_validRequest_authorized_return404()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/ingredients/{id}", id);

        doThrow(new NotFoundException()).when(ingredientService).delete(id);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_NOT_FOUND)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"CUSTOMER","STORE_OWNER"})
    @Test
    void delete_validRequest_Forbidden_return403()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/ingredients/{id}", 1);

        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void delete_validRequest_unauthorized_return401()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/ingredients/{id}", 1);
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }


}