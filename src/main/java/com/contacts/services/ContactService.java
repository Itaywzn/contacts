package com.contacts.services;

import com.contacts.entities.Address;
import com.contacts.entities.Contact;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface ContactService {

    List<Contact.NoRelations> getPage(Pageable pageable);

    Contact save(Contact contact);

    Contact update(Contact contact, long id);

    boolean deleteById(long id);

}
