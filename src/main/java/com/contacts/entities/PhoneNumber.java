package com.contacts.entities;

import com.contacts.entities.enums.PhoneType;
import com.contacts.entities.enums.converters.PhoneTypeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String number;



}
