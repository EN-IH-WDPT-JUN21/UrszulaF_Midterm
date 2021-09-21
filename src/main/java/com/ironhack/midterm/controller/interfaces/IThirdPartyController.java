package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.user.ThirdParty;

import java.util.List;

public interface IThirdPartyController {
    List<ThirdParty> getThirdParties();
}
