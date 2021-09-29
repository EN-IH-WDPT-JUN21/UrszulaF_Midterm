package com.ironhack.midterm.controller.interfaces;

import com.ironhack.midterm.dao.account.ThirdPartyTransactionSend;

import java.util.List;

public interface IThirdPartyTransactionSendController {
    List<ThirdPartyTransactionSend> getThirdPartyTransactionSends();
}
