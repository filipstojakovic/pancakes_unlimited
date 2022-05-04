package net.croz.pancakes_unlimited.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.models.dtos.PancakeDTO;
import net.croz.pancakes_unlimited.models.requests.PancakeRequest;
import net.croz.pancakes_unlimited.models.responses.PancakeIngredientResponse;
import net.croz.pancakes_unlimited.services.PancakeService;
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
import java.util.HashSet;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PancakeController.class)
class PancakeControllerTest
{
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static ObjectWriter objectWriter;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PancakeService pancakeService;
    @BeforeAll
    static void beforeAll()
    {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"CUSTOMER"})
    @Test
    void findAll_validRequest_authorized_return200AndResult()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/pancakes");

        PancakeDTO pancakeDTO1 = new PancakeDTO(1,List.of(new PancakeIngredientResponse(1,"americka palacinka",new BigDecimal("1.12"),new CategoryDTO(1,"baza"))));
        PancakeDTO pancakeDTO2 = new PancakeDTO(2,List.of(new PancakeIngredientResponse(2,"eurokrem",new BigDecimal("2"),new CategoryDTO(2,"fil"))));

        List<PancakeDTO> expectedIngredients = List.of(pancakeDTO1, pancakeDTO2);
        when(pancakeService.findAll()).thenReturn(expectedIngredients);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<PancakeDTO> resultIngredients = Arrays.asList(mapper.readValue(responseContent, PancakeDTO[].class));

        assertThat(resultIngredients).isEqualTo(expectedIngredients);
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE"})
    @Test
    void findAll_validRequest_Forbidden_return403()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/pancakes");
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findAll_validRequest_unauthorized_return401()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/pancakes");
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void findById_validRequest_authorized_return200AndResult()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/pancakes/{id}", id);

        PancakeDTO expectedResponse = new PancakeDTO(1,List.of(new PancakeIngredientResponse(1,"americka palacinka",new BigDecimal("1.12"),new CategoryDTO(1,"baza"))));
        when(pancakeService.findById(id)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        PancakeDTO resultResponse = mapper.readValue(responseContent, PancakeDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void findById_invalidRequest_authorized_return404()
    {
        int id = -1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/pancakes/{id}", id);
        when(pancakeService.findById(id)).thenThrow(new NotFoundException());

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_NOT_FOUND)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE"})
    @Test
    void findById_validRequest_Forbidden_return403()
    {
        int id = 5;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/pancakes/{id}", id);
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findById_validRequest_unauthorized_return401()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/pancakes/{id}", id);
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void insert_validRequest_authorized_return200AndResult()
    {
        PancakeRequest requestObject = new PancakeRequest(new HashSet<>(Arrays.asList(1,1,2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/pancakes")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        PancakeDTO expectedResponse = new PancakeDTO(1,
                List.of(new PancakeIngredientResponse(1,"americka palacinka",new BigDecimal("1.12"),new CategoryDTO(1,"baza")),
                        new PancakeIngredientResponse(2,"eurokrem",new BigDecimal("2"),new CategoryDTO(2,"fil"))
                        ));

        when(pancakeService.insert(requestObject))
                .thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_CREATED)).andReturn();

        verify(pancakeService).insert(requestObject);

        String responseContent = result.getResponse().getContentAsString();
        PancakeDTO resultResponse = mapper.readValue(responseContent, PancakeDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void insert_invalidRequest_authorized_return400()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/pancakes");
        //missing ingredients

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_BAD_REQUEST)).andReturn();

    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE"})
    @Test
    void insert_validRequest_Forbidden_return403()
    {
        PancakeRequest requestObject = new PancakeRequest(new HashSet<>(Arrays.asList(1,1,2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/pancakes")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void insert_validRequest_unauthorized_return401()
    {
        PancakeRequest requestObject = new PancakeRequest(new HashSet<>(Arrays.asList(1,1,2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/pancakes")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void update_validRequest_authorized_return200AndResult()
    {
        int id = 1;
        PancakeRequest requestObject = new PancakeRequest(new HashSet<>(Arrays.asList(1,1,2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/pancakes/{id}", id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        PancakeDTO expectedResponse = new PancakeDTO(1,
                List.of(new PancakeIngredientResponse(1,"americka palacinka",new BigDecimal("1.12"),new CategoryDTO(1,"baza")),
                        new PancakeIngredientResponse(2,"eurokrem",new BigDecimal("2"),new CategoryDTO(2,"fil"))
                ));
        when(pancakeService.update(id, requestObject))
                .thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        PancakeDTO resultResponse = mapper.readValue(responseContent, PancakeDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void update_validRequest_authorized_return404()
    {
        int id = -1;
        PancakeRequest requestObject = new PancakeRequest(new HashSet<>(Arrays.asList(1,1,2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/pancakes/{id}", id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        when(pancakeService.update(id, requestObject))
                .thenThrow(new NotFoundException());

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_NOT_FOUND)).andReturn();

    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void update_invalidRequest_authorized_return400()
    {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/pancakes/{id}", 1);
        //missing ingredients
        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_BAD_REQUEST)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE"})
    @Test
    void update_validRequest_Forbidden_return403()
    {
        PancakeRequest requestObject = new PancakeRequest(new HashSet<>(Arrays.asList(1,1,2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/pancakes/{id}", 1)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void update_validRequest_unauthorized_return401()
    {
        PancakeRequest requestObject = new PancakeRequest(new HashSet<>(Arrays.asList(1,1,2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/pancakes/{id}", 1)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void delete_validRequest_authorized_return200()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/pancakes/{id}", id);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();
        verify(pancakeService).delete(id);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void delete_validRequest_authorized_return404()
    {
        int id = -1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/pancakes/{id}", id);

        doThrow(new NotFoundException()).when(pancakeService).delete(id);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_NOT_FOUND)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE"})
    @Test
    void delete_validRequest_Forbidden_return403()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/pancakes/{id}", 1);
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void delete_validRequest_unauthorized_return401()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/pancakes/{id}", 1);
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }
}