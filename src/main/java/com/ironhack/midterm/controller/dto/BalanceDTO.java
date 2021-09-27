package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
//    @PositiveOrZero(message = "Balance can't be negative.")
    protected Money balance;
}
