package com.tosan.customer;

import com.tosan.customer.repositories.CustomerRepository;
import com.tosan.customer.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void defineCustomerTest(){
        Assertions.assertEquals("Successful", customerService.defineCustomer("ali", 12345557L, "2002-12-18", "12345678435", 0));
    }

    @Test
    public void getDepositsOfCustomerWrongNationalCode(){
        Assertions.assertThrows(IllegalStateException.class, () -> customerService.getDepositsOfCustomer(999999L));
    }


}
