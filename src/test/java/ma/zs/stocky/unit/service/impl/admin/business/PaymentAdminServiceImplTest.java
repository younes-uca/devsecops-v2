package ma.zs.stocky.unit.service.impl.admin.business;

import ma.zs.stocky.bean.core.business.Payment;
import ma.zs.stocky.dao.facade.core.business.PaymentDao;
import ma.zs.stocky.service.impl.admin.business.PaymentAdminServiceImpl;

import ma.zs.stocky.bean.core.business.Purchase ;
import ma.zs.stocky.bean.core.crm.PaymentType ;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;



import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class PaymentAdminServiceImplTest {

    @Mock
    private PaymentDao repository;
    private AutoCloseable autoCloseable;
    private PaymentAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new PaymentAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllPayment() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSavePayment() {
        // Given
        Payment toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeletePayment() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetPaymentById() {
        // Given
        Long idToRetrieve = 1L; // Example Payment ID to retrieve
        Payment expected = new Payment(); // You need to replace Payment with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Payment result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Payment constructSample(int i) {
		Payment given = new Payment();
        given.setPurchase(new Purchase(1L));
        given.setReference("reference-"+i);
        given.setPaymentType(new PaymentType(1L));
        given.setAmount(BigDecimal.TEN);
        return given;
    }

}
