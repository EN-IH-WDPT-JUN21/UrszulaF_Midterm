package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.dao.account.*;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.dao.user.Admin;
import com.ironhack.midterm.dao.user.Role;
import com.ironhack.midterm.dao.user.User;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.RoleRepository;
import com.ironhack.midterm.repository.UserRepository;
import com.ironhack.midterm.utils.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Role accountHolderRole;
    private Role adminRole;

    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;
    private AccountHolder accountHolder3;
    private AccountHolder accountHolder4;
    private AccountHolder accountHolder5;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        accountHolderRole= new Role("ACCOUNT_HOLDER");
        adminRole= new Role("ADMIN");
        List<Role> roles = roleRepository.saveAll(List.of(accountHolderRole, adminRole));

        accountHolder1 = new AccountHolder("Ula", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1990,8,17), new Address("Warsaw", "Poland", "Szembeka 1", 1000), "ula@gmail.pl");
        accountHolder2 = new AccountHolder("Adam", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1976,6,16), new Address("Poznan", "Poland", "Targ 12", 20000), "adam@gmail.pl");
        accountHolder3 = new AccountHolder("Maryla", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1951,7,27), new Address("London", "UK", "Main 1", 23000), "maryla@gmail.pl");
        accountHolder4 = new AccountHolder("Janusz", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1951,3,23), new Address("Heaven", "Poland", "Cloud 5", 34500), "janusz@gmail.pl");
        accountHolder5 = new AccountHolder("Zofia", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(2010,3,17), new Address("Lublin", "Poland", "Wola 1", 43000), "zofia@gmail.pl");

        List<AccountHolder> accountHolders = accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2, accountHolder3, accountHolder4, accountHolder5));
        User user1= userRepository.findByUsername(accountHolder1.getUsername()).get();
        User user2= userRepository.findByUsername(accountHolder2.getUsername()).get();
    }

    @AfterEach
    void tearDown() {
//        roleRepository.deleteAll();
//        accountHolderRepository.deleteAll();
    }

    @Test
    void getAccountHolders() throws Exception{
        MvcResult result = mockMvc.perform(get("/account-holders")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Ula"));
        assertTrue(result.getResponse().getContentAsString().contains("Adam"));
        assertTrue(result.getResponse().getContentAsString().contains("Maryla"));
    }

    @Test
    void getById() throws Exception{
        MvcResult result = mockMvc.perform(
                get("/account-holders/"+accountHolder1.getId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Ula"));
    }

//    @Test
//    void retrieveUser() {
//    }

//    @Test
//    void store() throws Exception{
//        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("Jan", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, "1951-07-27", new Address("London", "UK", "Main 1", 23000), "jan@gmail.pl");
//        String body = objectMapper.writeValueAsString(accountHolderDTO);
//        MvcResult result = mockMvc.perform(post("/account-holders/new").content(body)
//                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
//        assertTrue(result.getResponse().getContentAsString().contains("Jan"));
//    }
}