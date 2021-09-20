package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.dao.CheckingAccount;
import com.ironhack.midterm.dao.ThirdParty;
import com.ironhack.midterm.repository.CheckingAccountRepository;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.service.interfaces.ICheckingAccountService;
import com.ironhack.midterm.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThirdPartyService implements IThirdPartyService {
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public void update(Long id, ThirdParty thirdParty){
        Optional<ThirdParty> storedThirdParty = thirdPartyRepository.findById(id);
        if(storedThirdParty.isPresent()){
            thirdParty.setId(storedThirdParty.get().getId());
            thirdPartyRepository.save(thirdParty);
        }
    }
}
