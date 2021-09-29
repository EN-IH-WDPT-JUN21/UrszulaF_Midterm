package com.ironhack.midterm.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.account.Address;
import com.ironhack.midterm.dao.user.Role;
import com.ironhack.midterm.dao.user.User;
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

    protected String username;
    protected String password;

    private Long roleId;

    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "Date format must be YYYY-MM-DD")
    private String dateOfBirth;

    @Embedded
    private Address primaryAddress;

    private String mailingAddress;


}
