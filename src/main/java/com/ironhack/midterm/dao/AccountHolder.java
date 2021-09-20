package com.ironhack.midterm.dao;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountHolder extends User{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    private String name;

    private LocalDate DateOfBirth;

    @Embedded
    private Address primaryAddress;

    private String mailingAddress;

    @OneToMany(mappedBy = "primaryOwner", cascade = CascadeType.ALL)
    private List<Account> accountsPrimaryOwner;

//    @OneToMany(mappedBy = "secondaryOwner")
//    private List<Account> accountsS;
    @ManyToMany(mappedBy = "secondaryOwners", cascade = CascadeType.ALL)
    private List<Account> accountsSecondaryOwner;


    public LocalDate getDateOfBirth() {
        return DateOfBirth;
    }

    public int age() {
        LocalDate currentDate = LocalDate.now();
        if (DateOfBirth != null) {
            return Period.between(DateOfBirth, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
