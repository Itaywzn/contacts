package com.contacts.services.impl;

import com.contacts.entities.Address;
import com.contacts.repositories.AddressRepo;
import com.contacts.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepo addressRepo;

    @Autowired
    AddressServiceImpl(AddressRepo addressRepo){
        this.addressRepo = addressRepo;
    }
    @Override
    public Map<Long, Address> getAddressesMappedByID(Collection<Long> contactAddressesIds) {
        return addressRepo.findAllById(contactAddressesIds).stream()
                .collect(Collectors.toMap(Address::getId, Function.identity()));
    }
}
