package net.croz.pancakes_unlimited.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.CategoryDTO;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest
{
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void findAll_validRequest_Authorized_return200AndResultJson()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/categories");

        CategoryDTO cat1 = new CategoryDTO(1, "baza");
        CategoryDTO cat2 = new CategoryDTO(2, "fil");
        List<CategoryDTO> inputCategories = List.of(cat1, cat2);
        when(categoryService.findAll()).thenReturn(inputCategories);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<CategoryDTO> outputCategories = Arrays.asList(mapper.readValue(responseContent, CategoryDTO[].class));

        assertThat(outputCategories).isEqualTo(inputCategories);
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"CUSTOMER"})
    @Test
    void findAll_invalidRequest_Forbidden_return403()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/categories");
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findAll_invalidRequest_Unauthorized_return401()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/categories");
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void findById_validRequest_Authorized_return200AndResultJson()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/categories/{id}", id);

        CategoryDTO expectedValue = new CategoryDTO(id, "baza");
        when(categoryService.findById(id)).thenReturn(expectedValue);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        CategoryDTO outputCategories = mapper.readValue(responseContent, CategoryDTO.class);

        assertThat(outputCategories).isEqualTo(expectedValue);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"EMPLOYEE"})
    @Test
    void findById_invalidRequest_Authorized_return404()
    {
        int id = 5;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/categories/{id}", id);
        when(categoryService.findById(id)).thenThrow(new NotFoundException());

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_NOT_FOUND)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"CUSTOMER"})
    @Test
    void findById_validRequest_Forbidden_return403()
    {
        int id = 5;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/categories/{id}", id);
        when(categoryService.findById(id)).thenThrow(new NotFoundException());

        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findById_validRequest_Unauthorized_return401()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/categories/{id}", id);
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

}