package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.exceptions.ArgumentAddressException;
import com.example.alaa.university.exceptions.ResourceAddressIsNotFoundException;
import com.example.alaa.university.repository.IAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements IAddressService {
    private final IAddressRepository iAddRepository;

    public AddressService(IAddressRepository iRepository) {
        this.iAddRepository = iRepository;
    }

    @Override
    public Address get(Long id) {
        Address address = iAddRepository.get(id);
        if (address == null) {
            throw new ResourceAddressIsNotFoundException(
                    "address with id=" + id + " is does not found");
        }
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
        Address addressToAdded = iAddRepository.add(address);
        Long addressToAddId = addressToAdded.getId();
        return get(addressToAddId);
    }

    @Override
    public void delete(Long id) {
        get(id);
        iAddRepository.get(id);
        iAddRepository.delete(id);
    }

    @Override
    public List<Address> getAll() {
        return iAddRepository.getAll();
    }

    @Override
    public Address update(Long id, Address updatedAddress) {
        get(id);
        delete(id);
        Address newAddress = iAddRepository.add(updatedAddress);
        return get(newAddress.getId());
    }

    @Override
    public Address getStudentAddressId(Long studentId) {
        Address address = iAddRepository.getStudentAddressId(studentId);
        if (address == null) {
            throw new ResourceAddressIsNotFoundException("student with id="
                    + studentId + " does not have address");
        }
        return address;
    }

    @Override
    public Address getUniversityAddressId(Long universityId) {
        Address address = iAddRepository.getUniversityAddressId(universityId);
        if (address == null) {
            throw new ResourceAddressIsNotFoundException("University with id="
                    + " does not have address");
        }
        return address;
    }
}
