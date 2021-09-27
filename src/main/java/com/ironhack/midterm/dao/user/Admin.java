package com.ironhack.midterm.dao.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class Admin extends User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;

    public Admin(String username, String password, Role role) {
        super(username, password, role);
    }
}
