/*
package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.dao.account.Address;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.dao.user.Admin;
import com.ironhack.midterm.dao.user.Role;
import com.ironhack.midterm.dao.user.User;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    private Role accountHolderRole;
    private Role adminRole;

    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;
    private AccountHolder accountHolder3;
    private AccountHolder accountHolder4;
    private AccountHolder accountHolder5;

    private Admin admin1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    @Primary
    public CustomUserDetailsService userDetailsService() {
        accountHolderRole= new Role("ACCOUNT_HOLDER");
        adminRole= new Role("ADMIN");
        List<Role> roles = roleRepository.saveAll(List.of(accountHolderRole, adminRole));

        accountHolder1 = new AccountHolder("Ula", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1990,8,17), new Address("Warsaw", "Poland", "Szembeka 1", 1000), "ula@gmail.pl");
        accountHolder2 = new AccountHolder("Adam", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1976,6,16), new Address("Poznan", "Poland", "Targ 12", 20000), "adam@gmail.pl");
        accountHolder3 = new AccountHolder("Maryla", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1951,7,27), new Address("London", "UK", "Main 1", 23000), "maryla@gmail.pl");
        accountHolder4 = new AccountHolder("Janusz", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1951,3,23), new Address("Heaven", "Poland", "Cloud 5", 34500), "janusz@gmail.pl");
        accountHolder5 = new AccountHolder("Zofia", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(2010,3,17), new Address("Lublin", "Poland", "Wola 1", 43000), "zofia@gmail.pl");

        admin1 = new Admin("Boss", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", adminRole);

        List<AccountHolder> accountHolders = accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2, accountHolder3, accountHolder4, accountHolder5));
        List<Admin> admins = adminRepository.saveAll(List.of(admin1));
//        User basicUser = new UserImpl("Basic User", "user@company.com", "password");
//        UserActive basicActiveUser = new UserActive(basicUser, Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_USER"),
//                new SimpleGrantedAuthority("PERM_FOO_READ")
//        ));
//
//        User managerUser = new UserImpl("Manager User", "manager@company.com", "password");
//        UserActive managerActiveUser = new UserActive(managerUser, Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_MANAGER"),
//                new SimpleGrantedAuthority("PERM_FOO_READ"),
//                new SimpleGrantedAuthority("PERM_FOO_WRITE"),
//                new SimpleGrantedAuthority("PERM_FOO_MANAGE")
//        ));

        User user1= userRepository.findByUsername(accountHolder1.getUsername()).get();
        User user2= userRepository.findByUsername(accountHolder2.getUsername()).get();
        UserActive userActive = new UserActive();
        return new InMemoryUserDetailsManager(Arrays.asList(
                user1, user2
        ));
    }
}
*/
