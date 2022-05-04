package net.croz.pancakes_unlimited.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.croz.pancakes_unlimited.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CategoryController.class)
class IngredientControllerTest
{
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void findById()
    {
    }

    @Test
    void findAll()
    {
    }

    @Test
    void insert()
    {
    }

    @Test
    void update()
    {
    }

    @Test
    void delete()
    {
    }
}