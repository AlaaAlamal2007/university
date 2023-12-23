package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.exceptions.ArgumentAddressException;
import com.example.alaa.university.exceptions.ResourceAddressIsNotFoundException;
import com.example.alaa.university.repository.IAddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {
    @InjectMocks
    private AddressService addressServiceTest;
    @Mock
    private IAddressRepository iAddRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExistAddress() {
        when(iAddRepositoryTest.get(55L)).thenReturn(new Address());
        Address address = addressServiceTest.get(55L);
        assertNotNull(address);
    }

    @Test
    void getAddressThrowResourceAddressIsNotFoundException() {
        ResourceAddressIsNotFoundException addressIsNotFoundException
                = assertThrowsExactly(ResourceAddressIsNotFoundException.class, () -> addressServiceTest.get(55L));
        assertEquals("address with id=55 is does not found",
                addressIsNotFoundException.getMessage());
    }

    @Test
    void addSuccess() {
        Address address = new Address("Tamra", "Amru street", 10);
        when(iAddRepositoryTest.add(any())).thenAnswer((InvocationOnMock invocation) -> {
            Object firstArgument = invocation.getArgument(0);
            Address addressArg = (Address) firstArgument;
            addressArg.setId(4555L);
            return addressArg;
        });
        Address addressAdded = addressServiceTest.add(address);
        assertNotNull(addressAdded);
        assertNotNull(addressAdded.getId());
        assertEquals(4555L, addressAdded.getId());
    }

    @Test
    void validateStreetAddressNumberException() {
        Address address = Mockito.mock(Address.class);
        when(address.getStreetNumber()).thenThrow(new ArgumentAddressException("street number must no be negative"));
        ArgumentAddressException argumentAddressException =
                assertThrowsExactly(ArgumentAddressException.class, () -> address.getStreetNumber());
        assertEquals("street number must no be negative", argumentAddressException.getMessage());
    }

    @Test
    void addressAnyInputNullException() {
        Address address = Mockito.mock(Address.class);
        when(address.getCityName()).thenThrow(new ArgumentAddressException("every field of address must not be null"));
        ArgumentAddressException argumentAddressException =
                assertThrowsExactly(ArgumentAddressException.class, () -> address.getCityName());
        assertEquals("every field of address must not be null", argumentAddressException.getMessage());
    }

    @Test
    void delete() {
        Address address = new Address("Tmra", "a street", 5);
        address.setId(10L);
        when(iAddRepositoryTest.get(anyLong())).thenReturn(address);
        doNothing().when(iAddRepositoryTest).delete(anyLong());
        addressServiceTest.delete(10L);
        verify(iAddRepositoryTest, times(1)).delete(10L);
    }

    @Test
    void deleteAddressException() {
        ResourceAddressIsNotFoundException addressIsNotFoundException
                = assertThrowsExactly(ResourceAddressIsNotFoundException.class, () -> addressServiceTest.delete(55L));
        assertEquals("address with id=55 is does not found",
                addressIsNotFoundException.getMessage());
    }

    @Test
    void getAllSuccess() {
        Mockito.when(iAddRepositoryTest.getAll()).thenReturn(Arrays.asList(
                new Address(), new Address(), new Address()
        ));
        List<Address> addressArrayList = addressServiceTest.getAll();
        assertEquals(3, addressArrayList.size());
        assertNotNull(addressArrayList);
    }

    @Test
    void getAllEmpty() {
        Mockito.when(iAddRepositoryTest.getAll()).thenReturn(new ArrayList<>());
        List<Address> addressArrayList = addressServiceTest.getAll();
        assertNotNull(addressArrayList);
        assertEquals(0, addressArrayList.size());
    }

    @Test
    void getStudentAddressIdSuccess() {
        Mockito.when(iAddRepositoryTest.getStudentAddressId(anyLong())).thenReturn(new Address());
        Address address = addressServiceTest.getStudentAddressId(40L);
        assertNotNull(address);
        verify(iAddRepositoryTest).getStudentAddressId(40L);
    }

    @Test
    void getStudentAddressIdFailure() {
        when(iAddRepositoryTest.getStudentAddressId(anyLong())).thenReturn(null);
        ResourceAddressIsNotFoundException addressIsNotFoundException =
                assertThrowsExactly(ResourceAddressIsNotFoundException.class, () -> {
                    addressServiceTest.getStudentAddressId(60L);
                });
        assertEquals("student with id=60 does not have address", addressIsNotFoundException.getMessage());
    }

    @Test
    void getUniversityAddressIdSuccess() {
        when(iAddRepositoryTest.getUniversityAddressId(anyLong())).thenReturn(new Address());
        Address address = addressServiceTest.getUniversityAddressId(47L);
        assertNotNull(address);
        verify(iAddRepositoryTest).getUniversityAddressId(47L);
    }

    @Test
    void getUniversityAddressIdFailure() {
        when(iAddRepositoryTest.getUniversityAddressId(anyLong())).thenReturn(null);
        ResourceAddressIsNotFoundException addressIsNotFoundExceptionU =
                assertThrowsExactly(ResourceAddressIsNotFoundException.class, () -> {
                    addressServiceTest.getUniversityAddressId(50L);
                });
        assertEquals("University with id=50 does not have address", addressIsNotFoundExceptionU.getMessage());
    }

    @Test
    void updateSuccess(){
        Address address=new Address("ASD","ASF ST",9);
        address.setId(10L);
        Address oldAddress=new Address("ASDE","aer st",2);
        oldAddress.setId(11L);
        Mockito.when(iAddRepositoryTest.get(anyLong())).thenReturn(oldAddress);
        doNothing().when(iAddRepositoryTest).delete(anyLong());
        Mockito.when(addressServiceTest.add(address)).thenReturn(address);
        Mockito.when(iAddRepositoryTest.add(any())).thenReturn(address);
        Mockito.when(addressServiceTest.get(anyLong())).thenReturn(address);
        Address updatedAddress=addressServiceTest.update(11L,oldAddress);
        assertEquals(10L,updatedAddress.getId());
        assertNotNull(updatedAddress);
    }
}
