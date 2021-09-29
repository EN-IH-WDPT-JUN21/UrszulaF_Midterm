package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.ThirdPartyTransactionReceiveDTO;
import com.ironhack.midterm.controller.dto.ThirdPartyTransactionSendDTO;
import com.ironhack.midterm.controller.interfaces.IThirdPartyTransactionReceiveController;
import com.ironhack.midterm.dao.account.ThirdPartyTransactionReceive;
import com.ironhack.midterm.dao.account.ThirdPartyTransactionSend;
import com.ironhack.midterm.repository.*;
import com.ironhack.midterm.service.interfaces.IThirdPartyTransactionReceiveService;
import com.ironhack.midterm.service.interfaces.IThirdPartyTransactionSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ThirdPartyTransactionReceiveController implements IThirdPartyTransactionReceiveController {
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
    private ThirdPartyTransactionReceiveRepository thirdPartyTransactionReceiveRepository;

    @Autowired
    private IThirdPartyTransactionReceiveService thirdPartyTransactionService;

    @GetMapping("/third-party-receive-transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdPartyTransactionReceive> getThirdPartyTransactionReceives(){

        return thirdPartyTransactionReceiveRepository.findAll();
    }

    @GetMapping("/third-party-receive-transactions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ThirdPartyTransactionReceive getById(@PathVariable(name="id") long thirdPartyTransactionId){
        Optional<ThirdPartyTransactionReceive> optionalThirdPartyTransaction = thirdPartyTransactionReceiveRepository.findById(thirdPartyTransactionId);
        return optionalThirdPartyTransaction.isPresent()? optionalThirdPartyTransaction.get() : null;
    }


    @GetMapping("/third-party/receive-money/{hashed-key}")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdPartyTransactionReceive> getByHashedKey(@PathVariable(name="hashed-key") String hashedKey){
        List<ThirdPartyTransactionReceive> thirdPartyTransactionReceives = thirdPartyTransactionReceiveRepository.findByRecipientThirdPartyHashedKey(hashedKey);
        return thirdPartyTransactionReceives;
    }

    @PostMapping("/third-party/receive-money/{hashed-key}")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdPartyTransactionReceive transfer(@PathVariable(name="hashed-key") String hashedKey, @RequestBody @Valid ThirdPartyTransactionReceiveDTO thirdPartyTransactionReceiveDTO){


        return thirdPartyTransactionService.transfer(hashedKey, thirdPartyTransactionReceiveDTO);
    }
}
