package com.contacts.services.impl;

import com.contacts.entities.ContactGroup;
import com.contacts.repositories.ContactGroupRepo;
import com.contacts.services.ContactGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactGroupServiceImpl implements ContactGroupService {
    private ContactGroupRepo contactGroupRepo;

    @Autowired
    public ContactGroupServiceImpl(ContactGroupRepo contactGroupRepo) {
        this.contactGroupRepo = contactGroupRepo;
    }

    @Override
    public List<ContactGroup> getAll() {
        return contactGroupRepo.findAll();
    }

    @Override
    public ContactGroup save(ContactGroup contactGroup) {
        return contactGroupRepo.save(contactGroup);
    }
}
