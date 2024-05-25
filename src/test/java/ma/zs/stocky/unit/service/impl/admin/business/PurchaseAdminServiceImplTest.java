package ma.zs.stocky.unit.service.impl.admin.business;

import ma.zs.stocky.bean.core.business.Purchase;
import ma.zs.stocky.dao.facade.core.business.PurchaseDao;
import ma.zs.stocky.service.impl.admin.business.PurchaseAdminServiceImpl;

import ma.zs.stocky.bean.core.business.Payment ;
import ma.zs.stocky.bean.core.business.Purchase ;
import ma.zs.stocky.bean.core.crm.PaymentType ;
import ma.zs.stocky.bean.core.catalog.Product ;
import ma.zs.stocky.bean.core.business.PurchaseItem ;
import ma.zs.stocky.bean.core.crm.Client ;
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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class PurchaseAdminServiceImplTest {

    @Mock
    private PurchaseDao repository;
    private AutoCloseable autoCloseable;
    @Autowired
    private PurchaseAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest.setDao(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllPurchase() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSavePurchase() {
        // Given
        Purchase toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeletePurchase() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetPurchaseById() {
        // Given
        Long idToRetrieve = 1L; // Example Purchase ID to retrieve
        Purchase expected = new Purchase(); // You need to replace Purchase with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Purchase result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Purchase constructSample(int i) {
		Purchase given = new Purchase();
        given.setReference("reference-"+i);
        given.setPurchaseDate(LocalDateTime.now());
        given.setTotal(BigDecimal.TEN);
        given.setImage("image-"+i);
        given.setClient(new Client(1L));
        List<PurchaseItem> purchaseItems = IntStream.rangeClosed(1, 3)
                                             .mapToObj(id -> {
                                                PurchaseItem element = new PurchaseItem();
                                                element.setId((long)id);
                                                element.setProduct(new Product(Long.valueOf(1)));
                                                element.setPrice(new BigDecimal(2*10));
                                                element.setQuantity(new BigDecimal(3*10));
                                                element.setPurchase(new Purchase(Long.valueOf(4)));
                                                return element;
                                             })
                                             .collect(Collectors.toList());
        given.setPurchaseItems(purchaseItems);
        List<Payment> payments = IntStream.rangeClosed(1, 3)
                                             .mapToObj(id -> {
                                                Payment element = new Payment();
                                                element.setId((long)id);
                                                element.setPurchase(new Purchase(Long.valueOf(1)));
                                                element.setReference("reference"+id);
                                                element.setPaymentType(new PaymentType(Long.valueOf(3)));
                                                element.setAmount(new BigDecimal(4*10));
                                                return element;
                                             })
                                             .collect(Collectors.toList());
        given.setPayments(payments);
        return given;
    }

}
