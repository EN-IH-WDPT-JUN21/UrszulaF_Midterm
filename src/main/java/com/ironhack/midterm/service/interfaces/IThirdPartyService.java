package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.controller.dto.ThirdPartyDTO;
import com.ironhack.midterm.dao.user.ThirdParty;

public interface IThirdPartyService {
    ThirdParty store(ThirdPartyDTO thirdPartyDTO);
    void update(Long id, ThirdParty thirdParty);
}
