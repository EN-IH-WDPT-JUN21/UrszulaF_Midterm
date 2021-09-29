package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.ThirdPartyTransactionSendDTO;
import com.ironhack.midterm.controller.interfaces.IThirdPartyTransactionSendController;
import com.ironhack.midterm.dao.account.ThirdPartyTransactionSend;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.service.interfaces.IThirdPartyTransactionSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ThirdPartyTransactionSendController implements IThirdPartyTransactionSendController {
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
    private ThirdPartyTransactionSendRepository thirdPartyTransactionSendRepository;

    @Autowired
    private IThirdPartyTransactionSendService thirdPartyTransactionService;

    @GetMapping("/third-party-send-transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdPartyTransactionSend> getThirdPartyTransactionSends(){

        return thirdPartyTransactionSendRepository.findAll();
    }

    @GetMapping("/third-party-send-transactions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ThirdPartyTransactionSend getById(@PathVariable(name="id") long thirdPartyTransactionId){
        Optional<ThirdPartyTransactionSend> optionalThirdPartyTransaction = thirdPartyTransactionSendRepository.findById(thirdPartyTransactionId);
        return optionalThirdPartyTransaction.isPresent()? optionalThirdPartyTransaction.get() : null;
    }


    @GetMapping("/third-party/send-money/{hashed-key}")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdPartyTransactionSend> getByHashedKey(@PathVariable(name="hashed-key") String hashedKey){
        List<ThirdPartyTransactionSend> thirdPartyTransactionSends = thirdPartyTransactionSendRepository.findBySenderThirdPartyHashedKey(hashedKey);
        return thirdPartyTransactionSends;
    }

    @PostMapping("/third-party/send-money/{hashed-key}")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdPartyTransactionSend transfer(@PathVariable(name="hashed-key") String hashedKey, @RequestBody @Valid ThirdPartyTransactionSendDTO thirdPartyTransactionSendDTO){


        return thirdPartyTransactionService.transfer(hashedKey, thirdPartyTransactionSendDTO);
    }
}
