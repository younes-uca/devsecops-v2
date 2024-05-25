package ma.zs.stocky.unit.ws.facade.admin.crm;

import ma.zs.stocky.bean.core.crm.PaymentType;
import ma.zs.stocky.service.impl.admin.crm.PaymentTypeAdminServiceImpl;
import ma.zs.stocky.ws.facade.admin.crm.PaymentTypeRestAdmin;
import ma.zs.stocky.ws.converter.crm.PaymentTypeConverter;
import ma.zs.stocky.ws.dto.crm.PaymentTypeDto;
import org.aspectj.lang.annotation.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentTypeRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentTypeAdminServiceImpl service;
    @Mock
    private PaymentTypeConverter converter;

    @InjectMocks
    private PaymentTypeRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllPaymentTypeTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<PaymentTypeDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<PaymentTypeDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSavePaymentTypeTest() throws Exception {
        // Mock data
        PaymentTypeDto requestDto = new PaymentTypeDto();
        PaymentType entity = new PaymentType();
        PaymentType saved = new PaymentType();
        PaymentTypeDto savedDto = new PaymentTypeDto();

        // Mock the converter to return the paymentType object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved paymentType DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<PaymentTypeDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        PaymentTypeDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved paymentType DTO
        assertEquals(savedDto.getReference(), responseBody.getReference());
        assertEquals(savedDto.getCode(), responseBody.getCode());
    }

}
