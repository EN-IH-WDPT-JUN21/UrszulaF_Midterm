package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.Account;
import com.ironhack.midterm.dao.Transaction;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.repository.TransactionRepository;
import com.ironhack.midterm.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    //deposit method
    public void deposit(Long id, Money amount){
        Optional<Account> storedAccount = accountRepository.findById(id);
        BigDecimal zero= new BigDecimal("0");
        if(storedAccount.isEmpty()){
            System.out.println("There is no account with this id");
        }else{
            if (amount.getAmount().compareTo(zero)<=0) {
                System.out.println("Amount to be deposited should be positive");
            }else{
                Money balanceBefore = storedAccount.get().getBalance();
                BigDecimal balanceAfter = balanceBefore.increaseAmount(amount);
                storedAccount.get().setBalance(new Money(balanceAfter));
                Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount, storedAccount.get());
                transactionRepository.save(transaction);
                accountRepository.save(storedAccount.get());

                System.out.println(amount+" deposited");
            }

        }
    }
    //withdraw method
    public void withdraw(Long id, Money amount){
        Optional<Account> storedAccount = accountRepository.findById(id);
        BigDecimal zero= new BigDecimal("0");
        if(storedAccount.isEmpty()){
            System.out.println("There is no account with this id");
        }else{
            if (amount.getAmount().compareTo(zero)<=0) {
                System.out.println("Amount to be withdrawn should be positive");
            }else {
                Money balanceBefore = storedAccount.get().getBalance();
                BigDecimal balanceAfter = balanceBefore.decreaseAmount(amount);
                if (balanceAfter.compareTo(zero) < 0) {
                    System.out.println("Insufficient Balance");
                } else {
                    storedAccount.get().setBalance(new Money(balanceAfter));
                    Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, storedAccount.get());
                    transactionRepository.save(transaction);
                    accountRepository.save(storedAccount.get());
                    System.out.println(amount + " withdrawn");
                }
            }
        }

    }

    //withdraw method
    public void transfer(Long idFirst, Long idSecond, Money amount){
        Optional<Account> firstAccount = accountRepository.findById(idFirst);
        BigDecimal zero= new BigDecimal("0");
        if(firstAccount.isEmpty()){
            System.out.println("There is no account with this id");
        }else{
            if (amount.getAmount().compareTo(zero)<=0) {
                System.out.println("Amount to be transferred should be positive");
            }else {
                Money balanceBefore = firstAccount.get().getBalance();
                BigDecimal balanceAfter = balanceBefore.decreaseAmount(amount);
                if (balanceAfter.compareTo(zero) < 0) {
                    System.out.println("Insufficient Balance");
                } else {
                    Optional<Account> secondAccount = accountRepository.findById(idSecond);
                    firstAccount.get().setBalance(new Money(balanceAfter));


                    if(secondAccount.isEmpty()){
                        System.out.println("This is external transfer. We can't check the account number");
                        Transaction transaction = new Transaction(TransactionType.TRANSFER, amount, firstAccount.get());
                        transactionRepository.save(transaction);
                        accountRepository.save(firstAccount.get());
                    }else{
                        Money secondBalanceBefore = secondAccount.get().getBalance();
                        BigDecimal SecondBalanceAfter = secondBalanceBefore.increaseAmount(amount);
                        secondAccount.get().setBalance(new Money(SecondBalanceAfter));
                        Transaction transaction = new Transaction(TransactionType.TRANSFER, amount, firstAccount.get(),secondAccount.get());
                        transactionRepository.save(transaction);
                        accountRepository.save(firstAccount.get());
                        accountRepository.save(secondAccount.get());
                    }

                    System.out.println(amount + " transferred from " + idFirst + " account to " + idSecond + " account");
                }
            }
        }

    }


}
