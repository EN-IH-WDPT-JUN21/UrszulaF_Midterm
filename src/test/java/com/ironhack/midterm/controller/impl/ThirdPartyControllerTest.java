package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.controller.dto.ThirdPartyDTO;
import com.ironhack.midterm.dao.account.*;
import com.ironhack.midterm.dao.user.*;
import com.ironhack.midterm.repository.*;
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
class ThirdPartyControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

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


    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Autowired
    private StudentCheckingAccountRepository studentCheckingAccountRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Role accountHolderRole;
    private Role adminRole;

    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;
    private AccountHolder accountHolder3;
    private AccountHolder accountHolder4;
    private AccountHolder accountHolder5;

    private Admin admin1;
    private ThirdParty thirdParty1;
    private ThirdParty thirdParty2;

    private CheckingAccount checkingAccount1;
    private CreditCardAccount creditCardAccount1;
    private SavingAccount savingAccount1;
    private StudentCheckingAccount studentCheckingAccount1;


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

        admin1 = new Admin("Boss", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", adminRole);

        List<AccountHolder> accountHolders = accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2, accountHolder3, accountHolder4, accountHolder5));
        List<Admin> admins = adminRepository.saveAll(List.of(admin1));
        User user1= userRepository.findByUsername(accountHolder1.getUsername()).get();
        User user2= userRepository.findByUsername(accountHolder2.getUsername()).get();

        thirdParty1 = new ThirdParty("secret", "Jola");
        thirdParty2 = new ThirdParty("mine", "Jerzy");
        List<ThirdParty> thirdParties = thirdPartyRepository.saveAll(List.of(thirdParty1, thirdParty2));
    }


    @AfterEach
    void tearDown() {
//        roleRepository.deleteAll();
//        accountHolderRepository.deleteAll();
//        accountRepository.deleteAll();
    }

    @Test
    void getThirdParties() throws Exception{
        MvcResult result = mockMvc.perform(get("/third-parties")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Jola"));
        assertTrue(result.getResponse().getContentAsString().contains("Jerzy"));
    }

    @Test
    void store() throws Exception{

        ThirdPartyDTO thirdPartyNew = new ThirdPartyDTO("hers", "Ula");
        String body = objectMapper.writeValueAsString(thirdPartyNew);
        MvcResult result = mockMvc.perform(
                post("/third-parties/new")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Ula"));
    }
}