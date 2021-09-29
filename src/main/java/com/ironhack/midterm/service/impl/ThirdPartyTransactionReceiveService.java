package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.controller.dto.ThirdPartyTransactionReceiveDTO;
import com.ironhack.midterm.controller.dto.ThirdPartyTransactionSendDTO;
import com.ironhack.midterm.controller.dto.TransactionDTO;
import com.ironhack.midterm.dao.account.*;
import com.ironhack.midterm.dao.user.ThirdParty;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.service.interfaces.IThirdPartyTransactionReceiveService;
import com.ironhack.midterm.utils.CheckFraud;
import com.ironhack.midterm.utils.Constants;
import com.ironhack.midterm.utils.InterestRate;
import com.ironhack.midterm.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ThirdPartyTransactionReceiveService implements IThirdPartyTransactionReceiveService {
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
    ThirdPartyTransactionReceiveRepository thirdPartyTransactionReceiveRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    InterestRate interestRate;

    @Autowired
    CheckFraud checkFraud;
    
    //method to check the balance of the thirdPartyTransaction
    public List<ThirdPartyTransactionReceive> getMyThirdPartyTransactions(String hashedKey){
        List<ThirdPartyTransactionReceive> thirdPartyReceives = thirdPartyTransactionReceiveRepository.findByRecipientThirdPartyHashedKey(hashedKey);
        if(thirdPartyReceives.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no Third Party Transactions for this Third Party hashKey");
        }else{
            return thirdPartyReceives;
        }
    }

    //transfer method
    public ThirdPartyTransactionReceive transfer(String hashedKey, ThirdPartyTransactionReceiveDTO thirdPartyTransactionReceiveDTO){

        ////check user


        //checks if there is sender account
        Optional<Account> senderAccount = accountRepository.findById(thirdPartyTransactionReceiveDTO.getSenderAccountId());
        if(senderAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no account with this id");
        }
        //check fraud
        checkFraud.checkFraud(senderAccount.get());
        //interest rate applied for Credit card
        boolean senderIsCreditCardAccount= isCreditCardAccount(senderAccount.get());
        if(senderIsCreditCardAccount){
            interestRate.applyInterestRate((CreditCardAccount) senderAccount.get());
        }
        //interest rate applied for Saving Account, also checks minimum balance and apply penalty fee
        boolean senderIsSavingAccount= isSavingAccount(senderAccount.get());
        if(senderIsSavingAccount){
            interestRate.applyInterestRate((SavingAccount) senderAccount.get());
            checkBalanceAndApplyExtraFees((Penalizable) senderAccount.get(), thirdPartyTransactionReceiveDTO);
        }
        //checks minimum balance and apply penalty fee for Checking Account
        boolean senderIsCheckingAccount= isCheckingAccount(senderAccount.get());
        if(senderIsCheckingAccount){
            checkBalanceAndApplyExtraFees((Penalizable) senderAccount.get(), thirdPartyTransactionReceiveDTO);
        }
        //checks if enough funds to make transfer
        boolean enough = enoughFunds(senderAccount.get(), thirdPartyTransactionReceiveDTO.getAmount());
        if(!enough){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no enough funds on this account");
        }

        Money senderBalanceBefore = senderAccount.get().getBalance();
        BigDecimal senderBalanceAfter = senderBalanceBefore.decreaseAmount(thirdPartyTransactionReceiveDTO.getAmount());

        senderAccount.get().setBalance(new Money(senderBalanceAfter));
        ThirdPartyTransactionReceive thirdPartyTransactionReceive;
        //checks if there is recipient third party with this hash key
        Optional<ThirdParty> recipientAccount = thirdPartyRepository.findByHashedKey(hashedKey);
        if(recipientAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no Third Party with this hashKey " + hashedKey);
        }

        thirdPartyTransactionReceive = new ThirdPartyTransactionReceive(TransactionType.TRANSFER, thirdPartyTransactionReceiveDTO.getAmount(), senderAccount.get(),recipientAccount.get());
        thirdPartyTransactionReceiveRepository.save(thirdPartyTransactionReceive);
        accountRepository.save(senderAccount.get());


        System.out.println(thirdPartyTransactionReceiveDTO.getAmount() + " transferred from " + thirdPartyTransactionReceiveDTO.getSenderAccountId() + " account to Third Party with key " + hashedKey);
        return thirdPartyTransactionReceive;

    }

    private boolean accountsArePresent(String hashedKey, ThirdPartyTransactionReceiveDTO thirdPartyTransactionReceiveDTO) {
        return accountRepository.findById(thirdPartyTransactionReceiveDTO.getSenderAccountId()).isPresent()
                && thirdPartyRepository.findByHashedKey(hashedKey).isPresent();
    }


    private Account applyPenaltyFee(Penalizable penalizable) {
        penalizable.setBalance(new Money(penalizable.getBalance().getAmount().subtract(Constants.PENALTY_FEE)));
        accountRepository.save((Account) penalizable);
        return (Account) penalizable;
    }

    private void checkBalanceAndApplyExtraFees(Penalizable account, ThirdPartyTransactionReceiveDTO thirdPartyTransactionReceiveDTO) {
        if (account.getId().equals(thirdPartyTransactionReceiveDTO.getSenderAccountId())) {
            if (dropsBelowMinimumBalance(account, thirdPartyTransactionReceiveDTO.getAmount())) {
                if (!enoughFunds((Account) account, thirdPartyTransactionReceiveDTO.getAmount())) {
                    accountRepository.save((Account) account);
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sorry, but the account you are trying to transfer funds from does not have enough funds to perform this thirdPartyTransactionReceive");
                } else {
                    applyPenaltyFee(account);
                }
            }
        }
    }

    private boolean enoughFunds(Account account, Money amount) {
        boolean enoughFunds=true;
        BigDecimal balance = account.getBalance().getAmount();
        if(balance.compareTo(amount.getAmount())<0){
            enoughFunds=false;
        }
        return enoughFunds;
    }

    private boolean dropsBelowMinimumBalance(Penalizable account, Money amount) {
        boolean dropsBelowMinimumBalance=false;
        BigDecimal balance = account.getBalance().getAmount();
        BigDecimal minBalance = account.getMinimumBalance().getAmount();
        if(balance.compareTo(minBalance)<0){
            dropsBelowMinimumBalance=true;
        }
        return dropsBelowMinimumBalance;
    }

    private boolean isCreditCardAccount(Account account) {
        return creditCardAccountRepository.findById(account.getId()).isPresent();
    }

    private boolean isSavingAccount(Account account) {
        return savingAccountRepository.findById(account.getId()).isPresent();
    }

    private boolean isCheckingAccount(Account account) {
        return checkingAccountRepository.findById(account.getId()).isPresent();
    }
}
