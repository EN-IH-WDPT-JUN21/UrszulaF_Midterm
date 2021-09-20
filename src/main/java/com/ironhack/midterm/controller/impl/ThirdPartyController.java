package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.interfaces.IAccountController;
import com.ironhack.midterm.controller.interfaces.IThirdPartyController;
import com.ironhack.midterm.dao.Account;
import com.ironhack.midterm.dao.ThirdParty;
import com.ironhack.midterm.repository.AccountRepository;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ThirdPartyController implements IThirdPartyController {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @GetMapping("/thirdParties")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> getThirdParties(){

        return thirdPartyRepository.findAll();
    }
}
