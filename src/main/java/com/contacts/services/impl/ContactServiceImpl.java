package com.contacts.services.impl;

import com.contacts.entities.Address;
import com.contacts.entities.Contact;
import com.contacts.repositories.AddressRepo;
import com.contacts.repositories.ContactRepo;
import com.contacts.services.ContactService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepo contactRepo;

    @Autowired
    ContactServiceImpl(AddressRepo addressRepo, ContactRepo contactRepo){
        this.contactRepo = contactRepo;
    }

    @Override
    public List<Contact.NoRelations> getPage(Pageable pageable) {
        return contactRepo.findBy(pageable).getContent();
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepo.save(contact);
    }

    @Override
    @Transactional
    public Contact update(Contact contact, long id ) {
        Optional<Contact> optionalContact = contactRepo.findById(id);
        if (optionalContact.isPresent()){
            Contact dbContact = optionalContact.get();
            return contactRepo.save(mergeContact(dbContact,contact));
        }
        else{
            throw new RuntimeException("no such contact");
        }
    }

    private Contact mergeContact(Contact dbContact, Contact contact) {
        dbContact.setFirstName(contact.getFirstName());
        dbContact.setLastName(contact.getLastName());
        dbContact.setNickName(contact.getNickName());
        dbContact.setPhoneNumbers(contact.getPhoneNumbers());
        dbContact.setContactGroup(contact.getContactGroup());
        dbContact.setPhotoPath(contact.getPhotoPath());
        dbContact.setAddress(contact.getAddress());
        return dbContact;
    }

    @Override
    public boolean deleteById(long id) {
        try{
            contactRepo.deleteById(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
