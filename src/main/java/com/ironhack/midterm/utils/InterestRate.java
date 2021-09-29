package com.ironhack.midterm.utils;

import com.ironhack.midterm.dao.account.CreditCardAccount;
import com.ironhack.midterm.dao.account.SavingAccount;
import com.ironhack.midterm.repository.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        System.out.println("monthsBetween = " + monthsBetween);
        System.out.println("balance = " + creditCardAccount.getBalance().getAmount());
        System.out.println("rate= " + creditCardAccount.getInterestRate() );
        System.out.println("rate/12= " + creditCardAccount.getInterestRate()
                .divide(new BigDecimal("12"), RoundingMode.HALF_EVEN));
        System.out.println("capitalization= " +
                BigDecimal.ONE.add(
                        creditCardAccount.getInterestRate()
                                .divide(new BigDecimal("12"), RoundingMode.HALF_EVEN))
                        .pow(Math.toIntExact(monthsBetween)));
        System.out.println("result= " + creditCardAccount.getBalance().getAmount()
                .multiply(
                        BigDecimal.ONE.add(
                                creditCardAccount.getInterestRate()
                                        .divide(new BigDecimal("12"), RoundingMode.HALF_EVEN))
                                .pow(Math.toIntExact(monthsBetween))
                ));
        if (monthsBetween > 0 && creditCardAccount.getBalance().getAmount().compareTo(BigDecimal.ZERO) > 0) {
            //compound monthly interest applied
            creditCardAccount.setBalance(                     
                    new Money(creditCardAccount.getBalance().getAmount()
                            .multiply(
                                    BigDecimal.ONE.add(
                                            creditCardAccount.getInterestRate()
                                                    .divide(new BigDecimal("12"), RoundingMode.HALF_EVEN))
                                            .pow(Math.toIntExact(monthsBetween))
                            )));
;
            creditCardAccount.setLastInterestApplied(LocalDateTime.now());             
            creditCardAccountRepository.save(creditCardAccount);          
        }          return creditCardAccount;     
    }

    public SavingAccount applyInterestRate(SavingAccount savingAccount) {
        Long yearsBetween = ChronoUnit.YEARS.between(savingAccount.getLastInterestApplied(), LocalDateTime.now());
        if (yearsBetween > 0 && savingAccount.getBalance().getAmount().compareTo(BigDecimal.ZERO) > 0) {
            //compound yearly interest applied
            savingAccount.setBalance(
                    new Money(savingAccount.getBalance().getAmount()
                            .multiply(
                                    BigDecimal.ONE.add(
                                            savingAccount.getInterestRate()
                                                    )
                                            .pow(Math.toIntExact(yearsBetween))
                            )));

            savingAccount.setLastInterestApplied(LocalDateTime.now());
            savingAccountRepository.save(savingAccount);
        }          return savingAccount;
    }
}
