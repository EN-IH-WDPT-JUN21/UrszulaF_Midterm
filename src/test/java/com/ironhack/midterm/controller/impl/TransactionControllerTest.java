package com.ironhack.midterm.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.controller.dto.TransactionDTO;
import com.ironhack.midterm.controller.interfaces.ITransactionController;
import com.ironhack.midterm.dao.account.*;
import com.ironhack.midterm.dao.user.*;
import com.ironhack.midterm.enums.TransactionType;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TransactionControllerTest {

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

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ITransactionController transactionController;

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

    private Transaction transaction1;
    private Transaction transaction2;

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
        checkingAccount1.setCreationDate(LocalDateTime.of(2019,8,27,12,0));
        checkingAccountRepository.save(checkingAccount1);
        creditCardAccount1 = new CreditCardAccount(new Money(new BigDecimal(1000)), "55555", accountHolder2, List.of(accountHolder1), LocalDateTime.of(2021,7,28,12,0));
        creditCardAccountRepository.save(creditCardAccount1);
        savingAccount1 = new SavingAccount(new Money(new BigDecimal(1000)), "00000", accountHolder3, List.of(accountHolder5,accountHolder1));
        savingAccount1.setCreationDate(LocalDateTime.of(2020,7,27,12,0));
        savingAccountRepository.save(savingAccount1);
        studentCheckingAccount1 = new StudentCheckingAccount(new Money(new BigDecimal(1000)), "23232", accountHolder5, List.of());
        studentCheckingAccountRepository.save(studentCheckingAccount1);
        List<Account> accounts = accountRepository.saveAll(List.of(checkingAccount1, creditCardAccount1, savingAccount1, studentCheckingAccount1));
        transaction1 = new Transaction(TransactionType.TRANSFER, new Money(new BigDecimal(100)), checkingAccount1, creditCardAccount1);
        transaction1.setTimeStamp(LocalDateTime.of(2021,9,20,12,0));
        transaction2 = new Transaction(TransactionType.TRANSFER, new Money(new BigDecimal(200)), savingAccount1, studentCheckingAccount1);
        transaction2.setTimeStamp(LocalDateTime.of(2021,8,27,12,0));
        transactionRepository.saveAll(List.of(transaction1,transaction2));

    }

    @AfterEach
    void tearDown() {
//        roleRepository.deleteAll();
//        accountHolderRepository.deleteAll();
//        accountRepository.deleteAll();
    }

    @Test
    void getById() {
    }

    @Test
    void getTransactions() throws Exception{
        MvcResult result = mockMvc.perform(get("/transactions")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("100"));
        assertTrue(result.getResponse().getContentAsString().contains("200"));
    }

    @Test
    void transfer() throws Exception{
        BigDecimal senderAccountBalance0 = studentCheckingAccount1.getBalance().getAmount();
        BigDecimal recipientAccountBalance0 = checkingAccount1.getBalance().getAmount();
        TransactionDTO transaction1 = new TransactionDTO(TransactionType.TRANSFER, new Money(new BigDecimal(50)), studentCheckingAccount1.getId(), checkingAccount1.getId());
        String body = objectMapper.writeValueAsString(transaction1);
        MvcResult result = mockMvc.perform(
                post("/transfer")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("50"));
        BigDecimal transfer = transaction1.getAmount().getAmount();
        Account checkAccount= accountRepository.findById(transaction1.getSenderAccountId()).get();
        BigDecimal senderAccountBalance1 = checkAccount.getBalance().getAmount();
        Account checkAccount2= accountRepository.findById(transaction1.getRecipientAccountId()).get();
        BigDecimal recipientAccountBalance1 = checkAccount2.getBalance().getAmount();
        assertEquals(transfer, senderAccountBalance0.subtract(senderAccountBalance1));
    }

    @Test
    void transfer_withInterestRate() throws Exception{
        BigDecimal senderAccountBalance0 = creditCardAccount1.getBalance().getAmount();
        System.out.println("before interests=" + senderAccountBalance0);
        Long monthsBetween = ChronoUnit.MONTHS.between(creditCardAccount1.getLastInterestApplied(), LocalDateTime.now());
        System.out.println("monthsBetween = " + monthsBetween);
        System.out.println("rate= " + creditCardAccount1.getInterestRate().setScale(4, RoundingMode.HALF_EVEN));
        System.out.println("rate/12= " + creditCardAccount1.getInterestRate()
                .divide(new BigDecimal("12"), RoundingMode.HALF_EVEN).setScale(4, RoundingMode.HALF_EVEN));
        System.out.println("capitalization= " +
                BigDecimal.ONE.add(
                        creditCardAccount1.getInterestRate().setScale(4, RoundingMode.HALF_EVEN)
                                .divide(new BigDecimal("12"), RoundingMode.HALF_EVEN))
                        .pow(Math.toIntExact(monthsBetween)).setScale(4, RoundingMode.HALF_EVEN));
        BigDecimal senderAccountBalance0withInterests =                      creditCardAccount1.getBalance().getAmount()
                .multiply(
                        BigDecimal.ONE.add(
                                creditCardAccount1.getInterestRate().setScale(4, RoundingMode.HALF_EVEN)
                                        .divide(new BigDecimal("12"), RoundingMode.HALF_EVEN))
                                .pow(Math.toIntExact(monthsBetween))
                ).setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("after interests= " + senderAccountBalance0withInterests);
        BigDecimal recipientAccountBalance0 = studentCheckingAccount1.getBalance().getAmount();
        TransactionDTO transaction1 = new TransactionDTO(TransactionType.TRANSFER, new Money(new BigDecimal(50)), creditCardAccount1.getId(), studentCheckingAccount1.getId());
        String body = objectMapper.writeValueAsString(transaction1);
        MvcResult result = mockMvc.perform(
                post("/transfer")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("50"));
        BigDecimal transfer = transaction1.getAmount().getAmount();
        Account checkAccount= accountRepository.findById(transaction1.getSenderAccountId()).get();
        BigDecimal senderAccountBalance1 = checkAccount.getBalance().getAmount();

        Account checkAccount2= accountRepository.findById(transaction1.getRecipientAccountId()).get();
        BigDecimal recipientAccountBalance1 = checkAccount2.getBalance().getAmount();
        assertEquals(transfer, senderAccountBalance0withInterests.subtract(senderAccountBalance1));
    }
}