package com.ironhack.midterm.dao.account;


import javax.persistence.Embeddable;


@Embeddable
public class Address {
    private String country;

    private String city;

    private String streetAddress;

    private Integer postalCode;
}
