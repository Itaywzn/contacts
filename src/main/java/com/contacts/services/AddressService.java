package com.contacts.services;

import com.contacts.entities.Address;

import java.util.Collection;
import java.util.Map;
import org.springframework.stereotype.Service;


public interface AddressService {

    public Map<Long, Address> getAddressesMappedByID(Collection<Long> contactAddressesIds);
}
