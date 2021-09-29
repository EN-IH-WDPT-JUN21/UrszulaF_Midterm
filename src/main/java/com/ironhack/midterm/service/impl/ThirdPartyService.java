package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.controller.dto.ThirdPartyDTO;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.dao.user.ThirdParty;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ThirdPartyService implements IThirdPartyService {
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public ThirdParty store(ThirdPartyDTO thirdPartyDTO) {
        List<ThirdParty> thirdParty = thirdPartyRepository.findAll();
        ThirdParty newThirdParty = null;
        newThirdParty = new ThirdParty(thirdPartyDTO.getHashedKey(), thirdPartyDTO.getName());
        if (!thirdParty.contains(newThirdParty)) {
            return thirdPartyRepository.save(newThirdParty);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This thirdParty already exists in the system.");
        }
    }

    public void update(Long id, ThirdParty thirdParty){
        Optional<ThirdParty> storedThirdParty = thirdPartyRepository.findById(id);
        if(storedThirdParty.isPresent()){
            thirdParty.setId(storedThirdParty.get().getId());
            thirdPartyRepository.save(thirdParty);
        }
    }
}
