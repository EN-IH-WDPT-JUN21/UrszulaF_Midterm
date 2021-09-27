package com.ironhack.midterm.dao.account;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    private String country;

    private String city;

    private String streetAddress;

    private Integer postalCode;
}
