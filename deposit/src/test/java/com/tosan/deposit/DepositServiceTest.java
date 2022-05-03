package com.tosan.deposit;

import com.tosan.deposit.data.Deposit;
import com.tosan.deposit.data.DepositStatus;
import com.tosan.deposit.repositories.DepositRepository;
import com.tosan.deposit.services.DepositService;
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
public class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;

    @InjectMocks
    private DepositService depositService;

    @Test
    public void defrayNegativeBalance(){
        Mockito.when(depositRepository.findByDepositNumber(Matchers.any())).thenAnswer(t -> new Deposit(DepositStatus.OPEN));
        Assertions.assertThrows(IllegalStateException.class, () -> depositService.defray(12345679L,
                -500L));
    }

    @Test
    public void withdrawalNegativeBalance(){
        Mockito.when(depositRepository.findByDepositNumber(Matchers.any())).thenAnswer(t -> new Deposit(DepositStatus.OPEN));
        Assertions.assertThrows(IllegalStateException.class, () -> depositService.withdrawal(12345679L,
                -500L));
    }

    @Test
    public void transferNegativeBalance(){
        Assertions.assertThrows(IllegalStateException.class, () -> depositService.transfer(12345679L, 12344679L,
                -500L));
    }
}
