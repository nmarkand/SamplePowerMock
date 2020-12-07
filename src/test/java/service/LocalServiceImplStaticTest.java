package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TransactionUtils.class)
public class LocalServiceImplStaticTest {

    LocalService localService;

    @Test
    public void executeTransactionTest_mock_staticMethod() {
        localService = new LocalServiceImpl();

        PowerMockito.mockStatic(TransactionUtils.class);

        when(TransactionUtils.adjustBalance(500,500,10)).thenReturn(false);
        boolean transactionSucceed = localService.executeTransactions(10);
        assertFalse(transactionSucceed);

        PowerMockito.verifyStatic();
        TransactionUtils.adjustBalance(500,500,10);
    }
}
