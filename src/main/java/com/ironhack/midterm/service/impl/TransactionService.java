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
        //checks sender account
        Optional<Account> senderAccount = accountRepository.findById(transactionDTO.getSenderAccountId());
        if(senderAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no account with this id");
        }
        //                check fraud
        checkFraud(senderAccount.get());
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





    //transfer method for Credit Card Accounts
    public void transferCC(TransactionDTO transactionDTO){
        Optional<CreditCardAccount> senderCreditCardAccount = creditCardAccountRepository.findById(transactionDTO.getSenderAccountId());
        if(senderCreditCardAccount.isEmpty()){
            System.out.println("There is no creditCardAccount with this id");
        }else{
            //                check fraud
            checkFraud(senderCreditCardAccount.get());
            //            interest rate applied
            interestRate.applyInterestRate(senderCreditCardAccount.get());
            //checks if enough funds to make transfer
            boolean enough = enoughFunds(senderCreditCardAccount.get(), transactionDTO.getAmount());
            if(enough){
                Money senderBalanceBefore = senderCreditCardAccount.get().getBalance();
                BigDecimal senderBalanceAfter = senderBalanceBefore.decreaseAmount(transactionDTO.getAmount());
                Optional<Account> recipientAccount = accountRepository.findById(transactionDTO.getRecipientAccountId());
                senderCreditCardAccount.get().setBalance(new Money(senderBalanceAfter));


                if(recipientAccount.isEmpty()){
                    System.out.println("This is external transfer. We can't check the creditCardAccount number");
                    Transaction transaction = new Transaction(TransactionType.TRANSFER, transactionDTO.getAmount(), senderCreditCardAccount.get());
                    transactionRepository.save(transaction);
                    creditCardAccountRepository.save(senderCreditCardAccount.get());
                }else{
                    Money recipientBalanceBefore = recipientAccount.get().getBalance();
                    BigDecimal recipientBalanceAfter = recipientBalanceBefore.increaseAmount(transactionDTO.getAmount());
                    recipientAccount.get().setBalance(new Money(recipientBalanceAfter));
                    Transaction transaction = new Transaction(TransactionType.TRANSFER, transactionDTO.getAmount(), senderCreditCardAccount.get(),recipientAccount.get());
                    transactionRepository.save(transaction);
                    creditCardAccountRepository.save(senderCreditCardAccount.get());
                    accountRepository.save(recipientAccount.get());
                }

                System.out.println(transactionDTO.getAmount() + " transferred from " + transactionDTO.getSenderAccountId() + " creditCardAccount to " + transactionDTO.getRecipientAccountId() + " creditCardAccount");

            }
        }

    }

    //transfer method for Saving Accounts
    public void transferSA(TransactionDTO transactionDTO){
        Optional<SavingAccount> senderSavingAccount = savingAccountRepository.findById(transactionDTO.getSenderAccountId());
        if(senderSavingAccount.isEmpty()){
            System.out.println("There is no savingAccount with this id");
        }else{
            //                check fraud
            checkFraud(senderSavingAccount.get());
            //            interest rate applied
            interestRate.applyInterestRate(senderSavingAccount.get());
            //checks minimum balance and apply penalty fee
            checkBalanceAndApplyExtraFees(senderSavingAccount.get(), transactionDTO);
            //checks if enough funds to make transfer
            boolean enough = enoughFunds(senderSavingAccount.get(), transactionDTO.getAmount());
            if(enough){
                Money senderBalanceBefore = senderSavingAccount.get().getBalance();
                BigDecimal senderBalanceAfter = senderBalanceBefore.decreaseAmount(transactionDTO.getAmount());
                Optional<Account> recipientAccount = accountRepository.findById(transactionDTO.getRecipientAccountId());
                senderSavingAccount.get().setBalance(new Money(senderBalanceAfter));


                if(recipientAccount.isEmpty()){
                    System.out.println("This is external transfer. We can't check the savingAccount number");
                    Transaction transaction = new Transaction(TransactionType.TRANSFER, transactionDTO.getAmount(), senderSavingAccount.get());
                    transactionRepository.save(transaction);
                    savingAccountRepository.save(senderSavingAccount.get());
                }else{
                    Money recipientBalanceBefore = recipientAccount.get().getBalance();
                    BigDecimal recipientBalanceAfter = recipientBalanceBefore.increaseAmount(transactionDTO.getAmount());
                    recipientAccount.get().setBalance(new Money(recipientBalanceAfter));
                    Transaction transaction = new Transaction(TransactionType.TRANSFER, transactionDTO.getAmount(), senderSavingAccount.get(),recipientAccount.get());
                    transactionRepository.save(transaction);
                    savingAccountRepository.save(senderSavingAccount.get());
                    accountRepository.save(recipientAccount.get());
                }

                System.out.println(transactionDTO.getAmount() + " transferred from " + transactionDTO.getSenderAccountId() + " savingAccount to " + transactionDTO.getRecipientAccountId() + " savingAccount");
                
            }
        }

    }

    //transfer method for Checking Accounts
    public void transferCA(TransactionDTO transactionDTO){
        Optional<CheckingAccount> senderCheckingAccount = checkingAccountRepository.findById(transactionDTO.getSenderAccountId());
        if(senderCheckingAccount.isEmpty()){
            System.out.println("There is no checkingAccount with this id");
        }else{
            //                check fraud
            checkFraud(senderCheckingAccount.get());
            //checks minimum balance and apply penalty fee
            checkBalanceAndApplyExtraFees(senderCheckingAccount.get(), transactionDTO);
            //checks if enough funds to make transfer
            boolean enough = enoughFunds(senderCheckingAccount.get(), transactionDTO.getAmount());
            if(enough){
                Money senderBalanceBefore = senderCheckingAccount.get().getBalance();
                BigDecimal senderBalanceAfter = senderBalanceBefore.decreaseAmount(transactionDTO.getAmount());
                Optional<Account> recipientAccount = accountRepository.findById(transactionDTO.getRecipientAccountId());
                senderCheckingAccount.get().setBalance(new Money(senderBalanceAfter));


                if(recipientAccount.isEmpty()){
                    System.out.println("This is external transfer. We can't check the checkingAccount number");
                    Transaction transaction = new Transaction(TransactionType.TRANSFER, transactionDTO.getAmount(), senderCheckingAccount.get());
                    transactionRepository.save(transaction);
                    checkingAccountRepository.save(senderCheckingAccount.get());
                }else{
                    Money recipientBalanceBefore = recipientAccount.get().getBalance();
                    BigDecimal recipientBalanceAfter = recipientBalanceBefore.increaseAmount(transactionDTO.getAmount());
                    recipientAccount.get().setBalance(new Money(recipientBalanceAfter));
                    Transaction transaction = new Transaction(TransactionType.TRANSFER, transactionDTO.getAmount(), senderCheckingAccount.get(),recipientAccount.get());
                    transactionRepository.save(transaction);
                    checkingAccountRepository.save(senderCheckingAccount.get());
                    accountRepository.save(recipientAccount.get());
                }

                System.out.println(transactionDTO.getAmount() + " transferred from " + transactionDTO.getSenderAccountId() + " checkingAccount to " + transactionDTO.getRecipientAccountId() + " checkingAccount");

            }
        }

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

    private boolean senderIsCreditCardAccount(TransactionDTO transactionDTO) {
        return creditCardAccountRepository.findById(transactionDTO.getSenderAccountId()).isPresent();
    }

    private boolean senderIsSavingAccount(TransactionDTO transactionDTO) {
        return savingAccountRepository.findById(transactionDTO.getSenderAccountId()).isPresent();
    }

    private boolean senderIsCheckingAccount(TransactionDTO transactionDTO) {
        return checkingAccountRepository.findById(transactionDTO.getSenderAccountId()).isPresent();
    }
}
