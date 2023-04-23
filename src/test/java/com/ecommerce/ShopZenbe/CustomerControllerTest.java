package com.ecommerce.ShopZenbe;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import com.ecommerce.ShopZenbe.models.customer.*;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerResponseDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.UpdateCustomerDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private static final UUID customerId = UUID.randomUUID();
    private static CustomerDTO customerDTO;
    private static UpdateCustomerDTO updateCustomerDTO;
    private static List<CustomerResponseDTO> listOfCustomerResponse;
    private static CustomerResponseDTO customerResponse;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @BeforeAll
    public static void setUpAll() {
        customerResponse = new CustomerResponseDTO(customerId, "Alice", "Smith", "alice@gmail.com");

        listOfCustomerResponse = Arrays.asList(
                new CustomerResponseDTO(UUID.randomUUID(), "Alice", "Smith", "alice@gmail.com"),
                new CustomerResponseDTO(UUID.randomUUID(), "Bob", "Johnson", "john@gmail.com")
        );

        customerDTO = new CustomerDTO("Alice", "Smith", "alice@gmail.com");
        updateCustomerDTO = new UpdateCustomerDTO("Alice", "Smith", "smith@gmail.com");
    }

    @Test
    @DisplayName("Get All Customers Test")
    public void getAllCustomerTest() throws Exception {
        // Arrange: indicate the setup stage of a test (create the data to be used for the test)
        //  when() method specifies what should happen when a particular method is called on a mock object
        when(customerService.getAllCustomers()).thenReturn(listOfCustomerResponse);
        // ================================================================================================================

        // Act: indicate the action stage of a test (perform the test)
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer") // perform a get request
                        .accept(MediaType.APPLICATION_JSON)) // accept json response
                .andReturn();  // return the response
        // ================================================================================================================

        // Assert: indicate the assertion stage of a test (check if the test passed or failed)
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value()); // check if the response status code is 200

        String content = mvcResult.getResponse().getContentAsString(); // get the actual response

        // deserialize the response returned by the controller method into an ApiResponse object
        ApiResponse<List<CustomerResponseDTO>> response = new ObjectMapper().readValue(content, // ObjectMapper convert JSON data into Java objects(vice versa)
                new TypeReference<ApiResponse<List<CustomerResponseDTO>>>() {}); // TypeReference is used to specify the type of the object to be deserialized

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value()); // check if the response status code is 200
        assertThat(response.getMessage()).isEqualTo(""); // check if the response message is empty
        assertThat(response.getData()).isEqualTo(listOfCustomerResponse); // check if the response data is equal to the customers list
    }

    @Test
    @DisplayName("Get Customer by Id Test")
    public void getCustomerTest() throws Exception {
        // Arrange
        when(customerService.getCustomer(customerId)).thenReturn(customerResponse);

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/" + customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    // Assert
                    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    String content = result.getResponse().getContentAsString();

                    ApiResponse<CustomerResponseDTO> response = new ObjectMapper().readValue(content,
                            new TypeReference<ApiResponse<CustomerResponseDTO>>() {
                            });

                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
                    assertThat(response.getMessage()).isEqualTo("");
                    assertThat(response.getData()).isEqualTo(customerResponse);
                    assertThat(Objects.requireNonNull(response.getData()).getId()).isEqualTo(customerId);
                });
    }

    @Test
    @DisplayName("Add Customer Test")
    void registerCustomerTest() throws Exception {
        when(customerService.addCustomer(customerDTO)).thenReturn(customerResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
                    String content = result.getResponse().getContentAsString();

                    ApiResponse<CustomerResponseDTO> response = new ObjectMapper().readValue(content,
                            new TypeReference<ApiResponse<CustomerResponseDTO>>() {
                            });

                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
                    assertThat(response.getMessage()).isEqualTo("Customer created successfully!");
                    assertThat(response.getData()).isEqualTo(customerResponse);
                });
    }

    @Test
    @DisplayName("Update Customer Test")
    void updateCustomerTest() throws Exception {
        when(customerService.updateCustomer(customerId, updateCustomerDTO)).thenReturn(customerResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateCustomerDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                    String content = result.getResponse().getContentAsString();

                    ApiResponse<CustomerResponseDTO> response = new ObjectMapper().readValue(content,
                            new TypeReference<ApiResponse<CustomerResponseDTO>>() {
                            });

                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
                    assertThat(response.getMessage()).isEqualTo("Customer updated successfully!");
                    assertThat(response.getData()).isEqualTo(customerResponse);
                });
    }

}
