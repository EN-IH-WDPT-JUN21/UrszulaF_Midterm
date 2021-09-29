package com.ironhack.midterm.controller.impl;

import com.ironhack.midterm.controller.dto.AccountHolderDTO;
import com.ironhack.midterm.controller.dto.ThirdPartyDTO;
import com.ironhack.midterm.controller.interfaces.IThirdPartyController;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.dao.user.ThirdParty;
import com.ironhack.midterm.repository.ThirdPartyRepository;
import com.ironhack.midterm.service.interfaces.IAccountHolderService;
import com.ironhack.midterm.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ThirdPartyController implements IThirdPartyController {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private IThirdPartyService thirdPartyService;

    @GetMapping("/third-parties")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> getThirdParties(){

        return thirdPartyRepository.findAll();
    }

    //        only for admin
    @PostMapping("/third-parties/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty store(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        return thirdPartyService.store(thirdPartyDTO);
    }
}
