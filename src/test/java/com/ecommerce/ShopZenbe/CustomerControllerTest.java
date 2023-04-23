package com.ecommerce.ShopZenbe;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import com.ecommerce.ShopZenbe.models.customer.CustomerController;
import com.ecommerce.ShopZenbe.models.customer.CustomerResponseDTO;
import com.ecommerce.ShopZenbe.models.customer.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Test
    public void getAllCustomerTest() throws Exception {
        // Arrange: indicate the setup stage of a test (create the data to be used for the test)
        List<CustomerResponseDTO> customers = Arrays.asList(
                new CustomerResponseDTO(UUID.randomUUID(), "Alice", "Smith", "alice@gmail.com"),
                new CustomerResponseDTO(UUID.randomUUID(), "Bob", "Johnson", "john@gmail.com")
        );

        //  when() method specifies what should happen when a particular method is called on a mock object
        when(customerService.getAllCustomers()).thenReturn(customers);
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
        assertThat(response.getData()).isEqualTo(customers); // check if the response data is equal to the customers list
    }

    @Test
    public void getCustomerTest() throws Exception {
        // Arrange
        UUID customerId = UUID.randomUUID();
        CustomerResponseDTO customer = new CustomerResponseDTO(customerId, "Alice", "Smith", "alice@gmail.com");
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/" + customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        String content = mvcResult.getResponse().getContentAsString();

        ApiResponse<CustomerResponseDTO> response = new ObjectMapper().readValue(content,
                new TypeReference<ApiResponse<CustomerResponseDTO>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getMessage()).isEqualTo("");
        assertThat(response.getData()).isEqualTo(customer);
        assertThat(Objects.requireNonNull(response.getData()).getId()).isEqualTo(customerId);
    }
}
