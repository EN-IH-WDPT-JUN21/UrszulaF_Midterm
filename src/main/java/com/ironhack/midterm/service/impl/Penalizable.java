package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.utils.Money;

public interface Penalizable<T> {
    void setBalance(Money balance);

    Money getBalance();

    Money getMinimumBalance();

    Long getId();
}
