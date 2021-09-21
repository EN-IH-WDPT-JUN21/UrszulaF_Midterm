package com.ironhack.midterm.utils;

import com.ironhack.midterm.dao.account.CreditCardAccount;
import com.ironhack.midterm.dao.account.SavingAccount;
import com.ironhack.midterm.repository.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class InterestRate {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    SavingAccountRepository savingAccountRepository;


    public CreditCardAccount applyInterestRate(CreditCardAccount creditCardAccount) {
        Long monthsBetween = ChronoUnit.MONTHS.between(creditCardAccount.getLastInterestApplied(), LocalDateTime.now());         
        if (monthsBetween > 0 && creditCardAccount.getBalance().getAmount().compareTo(BigDecimal.ZERO) > 0) {             
            creditCardAccount.setBalance(                     
                    new Money(creditCardAccount.getBalance().getAmount()                             
                            .multiply(new BigDecimal(monthsBetween))                             
                            .multiply(                                     
                                    creditCardAccount.getInterestRate()                                             
                                            .divide(new BigDecimal("12")))));             
            creditCardAccount.setLastInterestApplied(LocalDateTime.now());             
            creditCardAccountRepository.save(creditCardAccount);          
        }          return creditCardAccount;     
    }

    public SavingAccount applyInterestRate(SavingAccount savingAccount) {
        Long monthsBetween = ChronoUnit.YEARS.between(savingAccount.getLastInterestApplied(), LocalDateTime.now());
        if (monthsBetween > 0 && savingAccount.getBalance().getAmount().compareTo(BigDecimal.ZERO) > 0) {
            savingAccount.setBalance(
                    new Money(savingAccount.getBalance().getAmount()
                            .multiply(new BigDecimal(monthsBetween))
                            .multiply(
                                    savingAccount.getInterestRate()
                                            )));
            savingAccount.setLastInterestApplied(LocalDateTime.now());
            savingAccountRepository.save(savingAccount);
        }          return savingAccount;
    }
}
