package com.ironhack.midterm.utils;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.account.CreditCardAccount;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.service.impl.Freezable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CheckFraud {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingAccountRepository checkingAccountRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    SavingAccountRepository savingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    //This method only takes accounts that have Status and evaluates if there has been fraud
    public void checkFraud(Freezable senderAccount) {
        //Retrieves the last transaction made in the last second (if exists)
        if (transactionRepository.findTransactionBySenderAccountAndTimeStampBetween((Account) senderAccount, LocalDateTime.now().minusSeconds(1), LocalDateTime.now()).isPresent()) {
            senderAccount.setStatus(Status.FROZEN);
            accountRepository.save((Account) senderAccount);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Transaction rejected: Your account is now frozen due to a potential fraud detected");
        }

        if (transactionRepository.getMaxByDay().isPresent() && transactionRepository.getSumLastTransactions(senderAccount.getId()).isPresent()) {

            //Compares the sum of the transactions on this account in the last 24 hours to the max sum of transactions in any day multiplied by 150%
            BigDecimal getMaxByDay = transactionRepository.getMaxByDay().get().multiply(new BigDecimal("1.5"));
            BigDecimal getLastDay = transactionRepository.getSumLastTransactions(senderAccount.getId()).get();

            if (getMaxByDay.compareTo(getLastDay) < 0) {

                senderAccount.setStatus(Status.FROZEN);
                accountRepository.save((Account) senderAccount);
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Transaction rejected: Your account is now frozen due to a potential fraud detected");
            }

        }

    }

    //Overridden method that applies only to credit cards. It only evaluates if there was a transaction in the last second
    private void checkFraud(CreditCardAccount creditCardAccount) {
        if (transactionRepository.findTransactionBySenderAccountAndTimeStampBetween((Account) creditCardAccount, LocalDateTime.now().minusSeconds(1), LocalDateTime.now()).isPresent()) {
            creditCardAccount.setStatus(Status.FROZEN);
            creditCardAccountRepository.save(creditCardAccount);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Transaction rejected: You cannot transfer money now due to a potential fraud detected");

        }
    }
}
