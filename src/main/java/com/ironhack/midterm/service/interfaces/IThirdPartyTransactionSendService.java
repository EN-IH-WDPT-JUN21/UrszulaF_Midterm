package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.controller.dto.ThirdPartyTransactionSendDTO;
import com.ironhack.midterm.dao.account.ThirdPartyTransactionSend;

public interface IThirdPartyTransactionSendService {
    ThirdPartyTransactionSend transfer(String hashedKey, ThirdPartyTransactionSendDTO thirdPartyTransactionSendDTO);
}
