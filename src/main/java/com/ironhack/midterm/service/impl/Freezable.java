package com.ironhack.midterm.service.impl;

import com.ironhack.midterm.enums.Status;

public interface Freezable<T> {
//    T freeze();
void setStatus(Status frozen);

    Long getId();
}
