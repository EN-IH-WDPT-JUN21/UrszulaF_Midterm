package com.ironhack.midterm.service.interfaces;

import com.ironhack.midterm.utils.Money;

public interface ITransactionService {
    void deposit(Long id, Money amount);
    void withdraw(Long id, Money amount);
    void transfer(Long senderAccountId, Long recipientAccountId, Money amount);
}
