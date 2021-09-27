package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.Security.CustomUserDetails;
import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.SavingAccountDTO;
import com.ironhack.midterm.controller.dto.StatusDTO;
import com.ironhack.midterm.dao.account.*;
import com.ironhack.midterm.dao.user.*;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.utils.Money;
import org.hibernate.type.LocalDateType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class AccountControllerTest {

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
        checkingAccount1 = new CheckingAccount(new Money(new BigDecimal(1000)), "12345", accountHolder1, List.of(accountHolder2));
        checkingAccountRepository.save(checkingAccount1);
        creditCardAccount1 = new CreditCardAccount(new Money(new BigDecimal(1000)), "55555", accountHolder2, List.of(accountHolder1));
        creditCardAccountRepository.save(creditCardAccount1);
        savingAccount1 = new SavingAccount(new Money(new BigDecimal(1000)), "00000", accountHolder3, List.of(accountHolder5,accountHolder1));
        savingAccountRepository.save(savingAccount1);
        studentCheckingAccount1 = new StudentCheckingAccount(new Money(new BigDecimal(1000)), "23232", accountHolder5, List.of());
        studentCheckingAccountRepository.save(studentCheckingAccount1);
        List<Account> accounts = accountRepository.saveAll(List.of(checkingAccount1, creditCardAccount1, savingAccount1, studentCheckingAccount1));
    }


    @AfterEach
    void tearDown() {
//        roleRepository.deleteAll();
//        accountHolderRepository.deleteAll();
//        accountRepository.deleteAll();
    }

    @Test
    void getAccounts() throws Exception{
        MvcResult result = mockMvc.perform(get("/accounts")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("12345"));
        assertTrue(result.getResponse().getContentAsString().contains("55555"));
        assertTrue(result.getResponse().getContentAsString().contains("00000"));
    }

    @Test
    void getById() throws Exception{
        MvcResult result = mockMvc.perform(
                get("/accounts/"+checkingAccount1.getId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("12345"));
    }
/*
    @Test
//    @WithMockUser(username="Ula", authorities ={"ACCOUNT_HOLDER"})
    @WithUserDetails(value="Ula")
    void testGetMyPrimaryAccounts() throws Exception{

                MvcResult result = mockMvc.perform(
                get("/my-accounts/primary/")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("12345"));
    }
*/
/*    @Test
    void store() throws Exception {
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("Jan", "$2a$10$MSzkrmfd5ZTipY0XkuCbAejBC9g74MAg2wrkeu8/m1wQGXDihaX3e", accountHolderRole, LocalDate.of(1951,7,27), new Address("London", "UK", "Main 1", 23000), "jan@gmail.pl");
        SavingAccount savingAccountDTO = new SavingAccountDTO(new Money(new BigDecimal(2000)), "88888", accountHolderDTO, List.of());
        String body = objectMapper.writeValueAsString(savingAccountDTO);
        MvcResult result = mockMvc.perform(
                post("/accounts")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("88888"));
    }*/
    @Test
    void updateBalance() throws Exception{
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(new Money(new BigDecimal("2000.00")));
        String body = objectMapper.writeValueAsString(balanceDTO);
        MvcResult result = mockMvc.perform(
                patch("/accounts/change-balance/"+checkingAccount1.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(new BigDecimal("2000.00"), accountRepository.findById(checkingAccount1.getId()).get().getBalance().getAmount());
    }
    @Test
    void updateStatus() throws Exception{
        StatusDTO statusDTO = new StatusDTO(Status.FROZEN);
        String body = objectMapper.writeValueAsString(statusDTO);
        MvcResult result = mockMvc.perform(
                patch("/accounts/change-status/"+checkingAccount1.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent()).andReturn();
        assertEquals(Status.FROZEN, accountRepository.findById(checkingAccount1.getId()).get().getStatus());
    }
/*
    @Test
    void delete() {
    }*/
}