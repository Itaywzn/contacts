package com.contacts.services;

import com.contacts.entities.Contact;
import com.contacts.entities.ContactGroup;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ContactGroupService {

    List<ContactGroup> getAll();

    ContactGroup save(ContactGroup contactGroup);
}
