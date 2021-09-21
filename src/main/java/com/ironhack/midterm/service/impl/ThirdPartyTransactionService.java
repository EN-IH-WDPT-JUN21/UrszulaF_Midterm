package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.controller.dto.ThirdPartyTransactionDTO;
import com.ironhack.midterm.controller.dto.TransactionDTO;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.account.ThirdPartyTransaction;
import com.ironhack.midterm.dao.account.Transaction;
import com.ironhack.midterm.dao.user.ThirdParty;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ThirdPartyTransactionService {
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
    ThirdPartyTransactionRepository thirdPartyTransactionRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    //transfer method
    public void transfer(String hashedKey, Long recipientAccountId, Money amount){
        Optional<ThirdParty> thirdPartyOptional = thirdPartyRepository.findByHashedKey(hashedKey);

        if(thirdPartyOptional.isEmpty()){
            System.out.println("In the database there is no Third Party with key " + hashedKey);
        }else{
            if (amount.getAmount().compareTo(BigDecimal.ZERO)<=0) {
                System.out.println("Amount to be transferred should be positive");
            }else {
                Optional<Account> recipientAccount = accountRepository.findById(recipientAccountId);

                if (recipientAccount.isEmpty()) {
                    System.out.println("There is no account with id: " + recipientAccountId);
                } else {
                    Money recipientBalanceBefore = recipientAccount.get().getBalance();
                    BigDecimal recipientBalanceAfter = recipientBalanceBefore.increaseAmount(amount);
                    recipientAccount.get().setBalance(new Money(recipientBalanceAfter));
                    ThirdPartyTransaction thirdPartyThirdPartyTransaction = new ThirdPartyTransaction(TransactionType.TRANSFER, amount, thirdPartyOptional.get(), recipientAccount.get());
                    thirdPartyTransactionRepository.save(thirdPartyThirdPartyTransaction);
                    accountRepository.save(recipientAccount.get());
                }

                System.out.println(amount + " transferred from Third Party with key " + hashedKey + " account to " + recipientAccountId + " account");
            }

        }

    }

    private boolean accountsArePresent(ThirdPartyTransactionDTO thirdPartyThirdPartyTransactionDTO) {
        return accountRepository.findById(thirdPartyThirdPartyTransactionDTO.getThirdPartyId()).isPresent()
                && accountRepository.findById(thirdPartyThirdPartyTransactionDTO.getRecipientAccountId()).isPresent();
    }
    
}
