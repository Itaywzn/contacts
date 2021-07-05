package com.contacts.repositories;


import com.contacts.entities.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {


    Slice<Contact.NoRelations> findBy(Pageable pageable);

    @Query(value = "Select * from contacts c Where c.first_name LIKE Concat(%,:nameToSearch,%) " +
            "OR c.last_name LIKE Concat(%,:nameToSearch,%) OR  c.nickname LIKE Concat(%,:nameToSearch,%) ", nativeQuery = true)
    Slice<Contact.NoRelations> findAllByNameLike(String nameToSearch);

    @Query(value = "Select c.* from contacts c inner join contact_group g on c.contact_group_id = c.id" +
            " Where  g.name LIKE Concat(%,:nameToSearch,%)", nativeQuery = true)
    Slice<Contact.NoRelations> findAllByContactGroup_name(String nameToSearch);

}
