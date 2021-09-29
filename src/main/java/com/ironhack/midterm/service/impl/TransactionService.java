package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.controller.dto.TransactionDTO;
import com.ironhack.midterm.dao.account.*;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.service.interfaces.ITransactionService;
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
public class TransactionService implements ITransactionService {
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
    
    @Autowired
    InterestRate interestRate;

    //method to check the balance of the transaction
    public List<Transaction> getMyTransactions(Long id){
        List<Transaction> senderAccountTransactions = transactionRepository.findBySenderAccountId(id);
        if(senderAccountTransactions.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no transactions for this transaction holder id");
        }else{
            return senderAccountTransactions;
        }
    }

    //deposit method
    public void deposit(Long id, Money amount){
        Optional<Account> storedAccount = accountRepository.findById(id);
        if(storedAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no account with this id");
        }else{
            if (amount.getAmount().compareTo(BigDecimal.ZERO)<=0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount to be deposited should be positive");
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
        if(storedAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no account with this id");
        }else{
//                check fraud
            checkFraud(storedAccount.get());
            if (amount.getAmount().compareTo(BigDecimal.ZERO)<=0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount to be withdrawn should be positive");
            }else {

                Money balanceBefore = storedAccount.get().getBalance();
                BigDecimal balanceAfter = balanceBefore.decreaseAmount(amount);
                if (balanceAfter.compareTo(BigDecimal.ZERO) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Balance");
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

    //withdraw method for Credit Card Accounts
    public void withdrawCC(Long id, Money amount){
        Optional<CreditCardAccount> storedCreditCardAccount = creditCardAccountRepository.findById(id);
        if(storedCreditCardAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no creditCardAccount with this id");
        }else{
            //                check fraud
            checkFraud(storedCreditCardAccount.get());
            if (amount.getAmount().compareTo(BigDecimal.ZERO)<=0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount to be withdrawn should be positive");
            }else {
                //            interest rate applied
                interestRate.applyInterestRate(storedCreditCardAccount.get());
                Money balanceBefore = storedCreditCardAccount.get().getBalance();
                BigDecimal balanceAfter = balanceBefore.decreaseAmount(amount);
                if (balanceAfter.compareTo(BigDecimal.ZERO) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Balance");
                } else {
                    storedCreditCardAccount.get().setBalance(new Money(balanceAfter));
                    Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, storedCreditCardAccount.get());
                    transactionRepository.save(transaction);
                    creditCardAccountRepository.save(storedCreditCardAccount.get());
                    System.out.println(amount + " withdrawn");
                }
            }
        }

    }

    //withdraw method for Saving Accounts
    public void withdrawSA(Long id, Money amount){
        Optional<SavingAccount> storedSavingAccount = savingAccountRepository.findById(id);
        if(storedSavingAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no savingAccount with this id");
        }else{
            //                check fraud
            checkFraud(storedSavingAccount.get());
            if (amount.getAmount().compareTo(BigDecimal.ZERO)<=0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount to be withdrawn should be positive");
            }else {
                //            interest rate applied
                interestRate.applyInterestRate(storedSavingAccount.get());
                Money balanceBefore = storedSavingAccount.get().getBalance();
                BigDecimal balanceAfter = balanceBefore.decreaseAmount(amount);
                if (balanceAfter.compareTo(BigDecimal.ZERO) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Balance");
                } else {
                    storedSavingAccount.get().setBalance(new Money(balanceAfter));
                    Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, storedSavingAccount.get());
                    transactionRepository.save(transaction);
                    savingAccountRepository.save(storedSavingAccount.get());
                    System.out.println(amount + " withdrawn");
                }
            }
        }

    }

    //transfer method
    public Transaction transfer(TransactionDTO transactionDTO){

        ////check user


        //checks if there is sender account
        Optional<Account> senderAccount = accountRepository.findById(transactionDTO.getSenderAccountId());
        if(senderAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no account with this id");
        }
        //check fraud
        checkFraud(senderAccount.get());
        //interest rate applied for Credit card
        boolean senderIsCreditCardAccount= isCreditCardAccount(senderAccount.get());
        if(senderIsCreditCardAccount){
        interestRate.applyInterestRate((CreditCardAccount) senderAccount.get());
        }
        //interest rate applied for Saving Account, also checks minimum balance and apply penalty fee
        boolean senderIsSavingAccount= isSavingAccount(senderAccount.get());
        if(senderIsSavingAccount){
            interestRate.applyInterestRate((SavingAccount) senderAccount.get());
            checkBalanceAndApplyExtraFees((Penalizable) senderAccount.get(), transactionDTO);
        }
        //checks minimum balance and apply penalty fee for Checking Account
        boolean senderIsCheckingAccount= isCheckingAccount(senderAccount.get());
        if(senderIsCheckingAccount){
            checkBalanceAndApplyExtraFees((Penalizable) senderAccount.get(), transactionDTO);
        }
        //checks if enough funds to make transfer
        boolean enough = enoughFunds(senderAccount.get(), transactionDTO.getAmount());
        if(!enough){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no enough funds on this account");
        }

        Money senderBalanceBefore = senderAccount.get().getBalance();
        BigDecimal senderBalanceAfter = senderBalanceBefore.decreaseAmount(transactionDTO.getAmount());

        Optional<Account> recipientAccount = accountRepository.findById(transactionDTO.getRecipientAccountId());
        senderAccount.get().setBalance(new Money(senderBalanceAfter));
        Transaction transaction;
        //checks recipient account
        if(recipientAccount.isEmpty()){
            System.out.println("This is external transfer. We can't check the account number");
            transaction = new Transaction(TransactionType.TRANSFER, transactionDTO.getAmount(), senderAccount.get());
            transactionRepository.save(transaction);
            accountRepository.save(senderAccount.get());
        }else{
            Money recipientBalanceBefore = recipientAccount.get().getBalance();
            BigDecimal recipientBalanceAfter = recipientBalanceBefore.increaseAmount(transactionDTO.getAmount());
            recipientAccount.get().setBalance(new Money(recipientBalanceAfter));
            transaction = new Transaction(TransactionType.TRANSFER, transactionDTO.getAmount(), senderAccount.get(),recipientAccount.get());
            transactionRepository.save(transaction);
            accountRepository.save(senderAccount.get());
            accountRepository.save(recipientAccount.get());
            }

        System.out.println(transactionDTO.getAmount() + " transferred from " + transactionDTO.getSenderAccountId() + " account to " + transactionDTO.getRecipientAccountId() + " account");
        return transaction;

    }

    private boolean accountsArePresent(TransactionDTO transactionDTO) {
        return accountRepository.findById(transactionDTO.getSenderAccountId()).isPresent()
                && accountRepository.findById(transactionDTO.getRecipientAccountId()).isPresent();
    }

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

    private Account applyPenaltyFee(Penalizable penalizable) {
        penalizable.setBalance(new Money(penalizable.getBalance().getAmount().subtract(Constants.PENALTY_FEE)));
        accountRepository.save((Account) penalizable);
        return (Account) penalizable;
    }

    private void checkBalanceAndApplyExtraFees(Penalizable account, TransactionDTO transactionDTO) {
        if (account.getId().equals(transactionDTO.getSenderAccountId())) {
            if (dropsBelowMinimumBalance(account, transactionDTO.getAmount())) {
                if (!enoughFunds((Account) account, transactionDTO.getAmount())) {
                    accountRepository.save((Account) account);
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sorry, but the account you are trying to transfer funds from does not have enough funds to perform this transaction");
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
