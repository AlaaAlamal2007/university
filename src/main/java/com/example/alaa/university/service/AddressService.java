package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.exceptions.ArgumentAddressException;
import com.example.alaa.university.exceptions.ResourceAddressIsNotFoundException;
import com.example.alaa.university.repository.AddressRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {
    private final AddressRepo addressRepo;

    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public Address get(Long id) {
        Address address = addressRepo.findById(id)
                .orElseThrow(
                        () -> new ResourceAddressIsNotFoundException(
                                "address with id=" + id + " is does not found")
                );
        return address;
    }

    @Override
    public Address add(Address address) {
        if (address.getCityName() == null || address.getStreetName() == null ||
                address.getStreetNumber() == null) {
            throw new ArgumentAddressException("every field of address must not be null");
        }
        if (address.getStreetNumber() < 0) {
            throw new ArgumentAddressException("street number must no be negative");
        }
        Address addressToAdded = addressRepo.save(address);
        return addressToAdded;
    }

    @Override
    public void delete(Long id) {
        Address address = addressRepo.findById(id).orElseThrow(
                () -> new ResourceAddressIsNotFoundException(
                        "address with id=" + id + " is does not found")
        );
        addressRepo.deleteById(id);
    }

    @Override
    public List<Address> getAll() {
        return addressRepo.findAll();
    }

    @Override
    public Address update(Long id, Address updatedAddress) {
        Address address = addressRepo.findById(id).orElseThrow(
                () -> new ResourceAddressIsNotFoundException(
                        "address with id=" + id + " is does not found")
        );
        addressRepo.deleteById(id);
        Address newAddress = addressRepo.save(updatedAddress);
        return newAddress;
    }
}
