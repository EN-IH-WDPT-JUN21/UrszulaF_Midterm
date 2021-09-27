package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.Security.CustomUserDetails;
import com.ironhack.midterm.controller.dto.AccountDTO;
import com.ironhack.midterm.controller.dto.BalanceDTO;
import com.ironhack.midterm.controller.dto.StatusDTO;
import com.ironhack.midterm.controller.interfaces.IAccountController;
import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.repository.AccountHolderRepository;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.service.interfaces.IAccountHolderService;
import com.ironhack.midterm.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountHolderService accountHolderService;

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts(){

        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getById(@PathVariable(name="id") long accountId){
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        return optionalAccount.isPresent()? optionalAccount.get() : null;
    }

//    to access own account by account holder
    @GetMapping("/my-accounts/primary")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getMyPrimaryAccounts(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String userName = customUserDetails.getUsername();
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername(userName);
        if(accountHolder.isPresent()){
            try{
                Long id = accountHolder.get().getId();
                return accountService.getMyPrimaryAccounts(id);
            }catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid.");
            }

        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user is not in the system.");
        }

    }

    @GetMapping("/my-accounts/secondary")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getMySecondaryAccounts(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String userName = customUserDetails.getUsername();
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername(userName);
        if(accountHolder.isPresent()){
            try{
                Long id = accountHolder.get().getId();
                return accountService.getMySecondaryAccounts(id);
            }catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid.");
            }

        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user is not in the system.");
        }

    }

//    only for admin

    @PatchMapping("/accounts/change-balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable(name = "id") Long id, @RequestBody @Valid BalanceDTO balanceDTO){
        accountService.updateBalance(id, balanceDTO.getBalance());
    }

    @PatchMapping("/accounts/change-status/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable(name = "id") Long id, @RequestBody @Valid StatusDTO statusDTO){
        accountService.updateStatus(id, statusDTO.getStatus());
    }

    @DeleteMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        accountRepository.deleteById(id);
    }
}
