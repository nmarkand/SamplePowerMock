package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalServiceImpl.class, TransactionUtils.class}) //Name of class whose private method need to be tested
// and name of class which contains static method
public class LocalServiceImplPrivateMethodTest {

    LocalService localService;

    @Captor
    ArgumentCaptor<Integer> argumentCaptorSourceBalance;

    @Captor
    ArgumentCaptor<Integer> argumentCaptorDestinationBalance;

    @Captor
    ArgumentCaptor<Integer> argumentCaptorTransactionAmount;

    /* Here we are invoking or using private method we are not mocking or stubbing a private method */
    @Test
    public void executeTransactionTest_invoke_privateMethod() throws Exception {
        localService = new LocalServiceImpl();

        Integer sourceAccountBalance = Whitebox.invokeMethod(localService, "getSourceAccountBalance");
        assertEquals(java.util.Optional.ofNullable(sourceAccountBalance), Optional.of(500));
    }

    /* Here we are mocking or stubbing a private method */
    @Test
    public void executeTransactionTest_mock_privateMethod() throws Exception {
        localService = spy(new LocalServiceImpl());
        PowerMockito.doReturn(150).when(localService, "getSourceAccountBalance");
        boolean executionSuccess = localService.executeTransactions(5);
        PowerMockito.verifyPrivate(localService, times(1)).invoke("getSourceAccountBalance");
    }

    /* Here we are mocking or stubbing private and static methods both */
    @Test
    public void executeTransactionTest_mock_privateAndStaticMethod() throws Exception {
        localService = spy(new LocalServiceImpl());
        PowerMockito.doReturn(140).when(localService, "getSourceAccountBalance");
        PowerMockito.doReturn(140).when(localService, "getDestinationAccountBalance");

        PowerMockito.mockStatic(TransactionUtils.class);
        when(TransactionUtils.adjustBalance(140,140,5)).thenReturn(true);

        boolean executionSuccess = localService.executeTransactions(5);
        PowerMockito.verifyPrivate(localService, times(1)).invoke("getSourceAccountBalance");
    }

    @Test
    public void executeTransactionTest_mock_privateAndStaticMethod_argumentCaptor() throws Exception {
        localService = spy(new LocalServiceImpl());
        PowerMockito.doReturn(140).when(localService, "getSourceAccountBalance");
        PowerMockito.doReturn(140).when(localService, "getDestinationAccountBalance");

        PowerMockito.mockStatic(TransactionUtils.class);
        when(TransactionUtils.adjustBalance(140,140,5)).thenReturn(true);

        boolean executionSuccess = localService.executeTransactions(5);
        PowerMockito.verifyPrivate(localService, times(1)).invoke("getSourceAccountBalance");

        PowerMockito.verifyStatic();
        TransactionUtils.adjustBalance(argumentCaptorSourceBalance.capture(), argumentCaptorDestinationBalance.capture(), argumentCaptorTransactionAmount.capture());

        assertThat(argumentCaptorSourceBalance.getValue(), is(140));
        assertThat(argumentCaptorDestinationBalance.getValue(), is(140));
        assertThat(argumentCaptorTransactionAmount.getValue(), is(5));
    }
}
