package com.contacts.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nickname")
    private String nickName;

    @JoinColumn(name = "address_id")
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private Address address;

    @Column(name = "address_id" , updatable = false, insertable = false)
    private Long addressId;

    @JoinColumn(name = "contact_group_id")
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    private ContactGroup contactGroup;

    @Column(name = "contact_group_id" , updatable = false, insertable = false)
    private Long contactGroupId;

    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST})
    @JoinTable()
    private Set<PhoneNumber> phoneNumbers;

    @Column(name = "photo_path")
    private String photoPath;

    public interface NoRelations {
        long getId();

        String getFirstName();

        String getLastName();

        String getNickName();

        Long getContactGroupId();

        Long getAddressId();

        String getPhotoPath();

        Set<PhoneNumber> getPhoneNumbers();
    }

}
