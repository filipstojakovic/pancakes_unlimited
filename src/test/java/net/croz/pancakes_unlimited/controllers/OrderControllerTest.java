package net.croz.pancakes_unlimited.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import net.croz.pancakes_unlimited.exceptions.NotFoundException;
import net.croz.pancakes_unlimited.models.dtos.OrderDTO;
import net.croz.pancakes_unlimited.models.requests.OrderRequest;
import net.croz.pancakes_unlimited.models.responses.OrderedPancakeResp;
import net.croz.pancakes_unlimited.services.OrderService;
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
import java.util.*;

import static javax.servlet.http.HttpServletResponse.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest
{

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static ObjectWriter objectWriter;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;

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
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders");

        OrderDTO orderDTO = new OrderDTO(1, new Date(), "cool description", UUID.randomUUID().toString(),
                List.of(new OrderedPancakeResp(1, new BigDecimal(2))),
                new BigDecimal(3), new BigDecimal(1), new BigDecimal(2));
        List<OrderDTO> expectedIngredients = List.of(orderDTO);
        when(orderService.findAll()).thenReturn(expectedIngredients);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<OrderDTO> resultIngredients = Arrays.asList(mapper.readValue(responseContent, OrderDTO[].class));

        assertThat(resultIngredients).isEqualTo(expectedIngredients);
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE", "STORE_OWNER"})
    @Test
    void findAll_validRequest_Forbidden_return403()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders");
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findAll_validRequest_unauthorized_return401()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders");
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void findById_validRequest_authorized_return200AndResult()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders/{id}", id);

        OrderDTO expectedResponse = new OrderDTO(1, new Date(), "cool description", UUID.randomUUID().toString(),
                List.of(new OrderedPancakeResp(1, new BigDecimal(2))),
                new BigDecimal(3), new BigDecimal(1), new BigDecimal(2));
        when(orderService.findById(id)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();
        verify(orderService).findById(id);

        String responseContent = result.getResponse().getContentAsString();
        OrderDTO resultResponse = mapper.readValue(responseContent, OrderDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void findById_invalidRequest_authorized_return404()
    {
        int id = -1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders/{id}", id);
        when(orderService.findById(id)).thenThrow(new NotFoundException());

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_NOT_FOUND)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE", "STORE_OWNER"})
    @Test
    void findById_validRequest_Forbidden_return403()
    {
        int id = 5;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders/{id}", id);
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findById_validRequest_unauthorized_return401()
    {
        int id = 1;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders/{id}", id);
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void findByOrderNumber_validRequest_authorized_return200AndResult()
    {
        String orderNumber = UUID.randomUUID().toString();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders/{orderNumber}", orderNumber);

        OrderDTO expectedResponse = new OrderDTO(1, new Date(), "cool description", UUID.randomUUID().toString(),
                List.of(new OrderedPancakeResp(1, new BigDecimal(2))),
                new BigDecimal(3), new BigDecimal(1), new BigDecimal(2));
        when(orderService.findByOrderNumber(orderNumber)).thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_OK)).andReturn();
        verify(orderService).findByOrderNumber(orderNumber);

        String responseContent = result.getResponse().getContentAsString();
        OrderDTO resultResponse = mapper.readValue(responseContent, OrderDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE", "STORE_OWNER"})
    @Test
    void findByOrderNumber_validRequest_Forbidden_return403()
    {
        String orderNumber = UUID.randomUUID().toString();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders/{orderNumber}", orderNumber);
        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void findByOrderNumber_validRequest_unauthorized_return401()
    {
        String orderNumber = UUID.randomUUID().toString();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/orders/{orderNumber}", orderNumber);
        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void insert_validRequest_authorized_return200AndResult()
    {
        OrderRequest requestObject = new OrderRequest("cool description", new HashSet<>(Arrays.asList(1, 1, 2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/orders")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        OrderDTO expectedResponse = new OrderDTO(1, new Date(), "cool description", UUID.randomUUID().toString(),
                List.of(new OrderedPancakeResp(1, new BigDecimal(2))),
                new BigDecimal(3), new BigDecimal(1), new BigDecimal(2));

        when(orderService.insert(requestObject))
                .thenReturn(expectedResponse);

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_CREATED)).andReturn();

        verify(orderService).insert(requestObject);

        String responseContent = result.getResponse().getContentAsString();
        OrderDTO resultResponse = mapper.readValue(responseContent, OrderDTO.class);

        assertThat(resultResponse).isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @WithMockUser(username = "q", roles = {"CUSTOMER"})
    @Test
    void insert_invalidRequest_authorized_return400()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/orders");
        //missing pancakes and description

        MvcResult result = mockMvc.perform(request).andExpect(status().is(SC_BAD_REQUEST)).andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "w", roles = {"EMPLOYEE", "STORE_OWNER"})
    @Test
    void insert_validRequest_Forbidden_return403()
    {
        OrderRequest requestObject = new OrderRequest("cool description", new HashSet<>(Arrays.asList(1, 1, 2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/orders")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_FORBIDDEN));
    }

    @SneakyThrows
    @Test
    void insert_validRequest_unauthorized_return401()
    {
        OrderRequest requestObject = new OrderRequest("cool description", new HashSet<>(Arrays.asList(1, 1, 2)));
        String requestObjectAsJson = objectWriter.writeValueAsString(requestObject);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/orders")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestObjectAsJson);

        mockMvc.perform(request).andExpect(status().is(SC_UNAUTHORIZED));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}