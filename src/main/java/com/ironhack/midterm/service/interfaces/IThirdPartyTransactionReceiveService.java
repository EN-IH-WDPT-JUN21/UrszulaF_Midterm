package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.controller.dto.ThirdPartyTransactionReceiveDTO;
import com.ironhack.midterm.controller.dto.ThirdPartyTransactionSendDTO;
import com.ironhack.midterm.dao.account.ThirdPartyTransactionReceive;
import com.ironhack.midterm.dao.account.ThirdPartyTransactionSend;

public interface IThirdPartyTransactionReceiveService {
    ThirdPartyTransactionReceive transfer(String hashedKey, ThirdPartyTransactionReceiveDTO thirdPartyTransactionReceiveDTO);
}
