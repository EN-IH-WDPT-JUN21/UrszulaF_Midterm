package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.controller.dto.ThirdPartyTransactionSendDTO;
import com.ironhack.midterm.dao.account.*;
import com.ironhack.midterm.dao.user.ThirdParty;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.service.interfaces.IThirdPartyTransactionSendService;
import com.ironhack.midterm.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ThirdPartyTransactionSendService implements IThirdPartyTransactionSendService {
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
    ThirdPartyTransactionSendRepository thirdPartyTransactionSendRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    //method to check the balance of the thirdPartyTransaction
    public List<ThirdPartyTransactionSend> getMyThirdPartyTransactions(String hashedKey){
        List<ThirdPartyTransactionSend> senderAccountThirdPartyTransactionSends = thirdPartyTransactionSendRepository.findBySenderThirdPartyHashedKey(hashedKey);
        if(senderAccountThirdPartyTransactionSends.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no Third Party Transactions for this Third Party hashKey");
        }else{
            return senderAccountThirdPartyTransactionSends;
        }
    }

    //transfer method
    public ThirdPartyTransactionSend transfer(String hashedKey, ThirdPartyTransactionSendDTO thirdPartyTransactionSendDTO){

        ////check user


        //checks if there is sender third party with this hash key
        Optional<ThirdParty> senderAccount = thirdPartyRepository.findByHashedKey(hashedKey);
        if(senderAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no Third Party with this hashKey " + hashedKey);
        }


        Optional<Account> recipientAccount = accountRepository.findById(thirdPartyTransactionSendDTO.getRecipientAccountId());

        ThirdPartyTransactionSend thirdPartyTransactionSend;
        //checks recipient account
        if(recipientAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no Recipient with this id " + thirdPartyTransactionSendDTO.getRecipientAccountId());
        }
        if(!recipientAccount.get().getSecretKey().equals(thirdPartyTransactionSendDTO.getSecretKey())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipient secret key " + thirdPartyTransactionSendDTO.getSecretKey() + " doesn't match with account id " + thirdPartyTransactionSendDTO.getRecipientAccountId());
        }
        if (thirdPartyTransactionSendDTO.getAmount().getAmount().compareTo(BigDecimal.ZERO)<=0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no enough funds on this account");
        }
        Money recipientBalanceBefore = recipientAccount.get().getBalance();
        BigDecimal recipientBalanceAfter = recipientBalanceBefore.increaseAmount(thirdPartyTransactionSendDTO.getAmount());
        recipientAccount.get().setBalance(new Money(recipientBalanceAfter));
        thirdPartyTransactionSend = new ThirdPartyTransactionSend(TransactionType.TRANSFER, thirdPartyTransactionSendDTO.getAmount(), senderAccount.get(),recipientAccount.get());
        thirdPartyTransactionSendRepository.save(thirdPartyTransactionSend);
        accountRepository.save(recipientAccount.get());


        System.out.println(thirdPartyTransactionSendDTO.getAmount() + " transferred from Third Party with key " + hashedKey + " to " + thirdPartyTransactionSendDTO.getRecipientAccountId() + " account");
        return thirdPartyTransactionSend;

    }
    

    private boolean accountsArePresent(String hashedKey, ThirdPartyTransactionSendDTO thirdPartyThirdPartyTransactionSendDTO) {
        return thirdPartyRepository.findByHashedKey(hashedKey).isPresent()
                && accountRepository.findById(thirdPartyThirdPartyTransactionSendDTO.getRecipientAccountId()).isPresent();
    }
    
}
