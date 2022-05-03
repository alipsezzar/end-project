package com.tosan.transaction;


import com.tosan.transaction.data.Transaction;
import com.tosan.transaction.repositories.TransactionRepository;
import com.tosan.transaction.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void happyRegisterTransaction(){
        Mockito.when(transactionRepository.save(Matchers.any())).thenAnswer(t -> new Transaction());
        Assertions.assertEquals("Transaction added successfully", transactionService.registerTransaction(
                0L, 5000L, 0L, 123456L, 987L));
    }
}
