package com.example.alaa.university.controller;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.service.IAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/addresses")
@RestController
public class AddressController {
    private final IAddressService iAddressServiceC;

    public AddressController(IAddressService iAddressServiceC) {
        this.iAddressServiceC = iAddressServiceC;
    }

    @GetMapping(path = "/{id}")
    public Address getOne(@PathVariable Long id) {
        return iAddressServiceC.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Address add(@RequestBody Address address) {
        System.out.println("enter add address method");
        return iAddressServiceC.add(address);
    }

    @DeleteMapping(path = "/{id}")
    void delete(@PathVariable Long id) {
        System.out.println("delete method called");
        iAddressServiceC.delete(id);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Address update(@PathVariable Long id, @RequestBody Address updatedAddress) {
        return iAddressServiceC.update(id, updatedAddress);
    }

    @GetMapping
    List<Address> getAll() {
        return iAddressServiceC.getAll();
    }
}

