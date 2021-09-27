package com.ironhack.midterm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.account.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderDTO {
    @NotEmpty(message = "Name can't be empty or null.")
    protected String username;
    protected String password;

    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "Date format must be YYYY-MM-DD")
    private String DateOfBirth;

    private String country;

    private String city;

    private String streetAddress;

    @Digits(integer = 10, fraction = 0, message = "Maximum number of digits for postalCode is 10, no decimals.")
    private String postalCode;

    private String mailingAddress;

//    public AccountHolderDTO(String username, String password, String dateOfBirth, String country, String city, String streetAddress, String postalCode, String mailingAddress) {
//        this.username = username;
//        this.password = password;
//        DateOfBirth = dateOfBirth;
//        this.country = country;
//        this.city = city;
//        this.streetAddress = streetAddress;
//        this.postalCode = postalCode;
//        this.mailingAddress = mailingAddress;
//    }
}
