package com.contacts.repositories;


import com.contacts.entities.Contact;
import com.contacts.entities.ContactGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactGroupRepo extends JpaRepository<ContactGroup, Long> {
}
