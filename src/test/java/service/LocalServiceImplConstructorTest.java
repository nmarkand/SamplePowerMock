package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalServiceImpl.class}) //Name of class in which constructor exists and needs to be mocked
public class LocalServiceImplConstructorTest {

    LocalService localService;

    @Mock
    LocalDbService localDbService;

    @Test //Here we are mocking constructor.
    public void storeTransactionTest_mockConstructorTest() throws Exception {
        localService = new LocalServiceImpl();

        PowerMockito.whenNew(LocalDbService.class).withNoArguments().thenReturn(localDbService);

        localService.storeTransaction();

        /* verifyNew verifies how many time constructor/object created */
        PowerMockito.verifyNew(LocalDbService.class, times(1)).withNoArguments();
    }

    @Test //Here we are mocking/stubbing constructor as well as mocking further LocalDbService method.
    public void storeTransactionTest() throws Exception {
        localService = new LocalServiceImpl();

        when(localDbService.saveTransaction(1010101)).thenReturn(true);
        PowerMockito.whenNew(LocalDbService.class).withNoArguments().thenReturn(localDbService);

        boolean stored = localService.storeTransaction();

        PowerMockito.verifyNew(LocalDbService.class, times(1)).withNoArguments();
        verify(localDbService, times(1)).saveTransaction(1010101);
        assertTrue(stored);
    }

}
