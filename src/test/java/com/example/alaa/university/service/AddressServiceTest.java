package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.exceptions.ArgumentAddressException;
import com.example.alaa.university.exceptions.ResourceAddressIsNotFoundException;
import com.example.alaa.university.repository.AddressRepo;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {
    @InjectMocks
    private AddressService addressServiceTest;
    @Mock
    private AddressRepo addressRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExistAddress() {
        Address address = new Address();
        address.setId(55L);
        when(addressRepo.findById(55L)).thenReturn(Optional.of(address));
        Address getAddress = addressServiceTest.get(55L);
        assertNotNull(address);
        assertEquals(55L, getAddress.getId());
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
        when(addressRepo.save(any())).thenAnswer((InvocationOnMock invocation) -> {
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
        when(addressRepo.findById(anyLong())).thenReturn(Optional.of(address));
        doNothing().when(addressRepo).deleteById(10L);
        addressServiceTest.delete(10L);
        verify(addressRepo, times(1)).deleteById(10L);
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
        Mockito.when(addressRepo.findAll()).thenReturn(Arrays.asList(
                new Address(), new Address(), new Address()
        ));
        List<Address> addressArrayList = addressServiceTest.getAll();
        assertEquals(3, addressArrayList.size());
        assertNotNull(addressArrayList);
    }

    @Test
    void getAllEmpty() {
        Mockito.when(addressRepo.findAll()).thenReturn(new ArrayList<>());
        List<Address> addressArrayList = addressServiceTest.getAll();
        assertNotNull(addressArrayList);
        assertEquals(0, addressArrayList.size());
    }

    @Test
    void updateAddress() {
        Address oldAddress = new Address();
        oldAddress.setId(13L);
        Address newAddress = new Address();
        newAddress.setId(40L);
        Mockito.when(addressRepo.findById(anyLong())).thenReturn(Optional.of(oldAddress));
        doNothing().when(addressRepo).deleteById(40L);
        Mockito.when(addressRepo.save(any())).thenReturn(newAddress);
        Address updatedAddress = addressServiceTest.update(13L, newAddress);
        assertNotNull(updatedAddress);
        assertEquals(40L, updatedAddress.getId());
    }
}
