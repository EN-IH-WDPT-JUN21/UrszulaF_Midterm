package com.ironhack.midterm.utils;

import java.math.BigDecimal;

public class Constants {
    public static final BigDecimal CCARD_ACC_DEFAULT_INTEREST_RATE = new BigDecimal("0.2");
    public static final BigDecimal CCARD_ACC_MIN_INTEREST_RATE = new BigDecimal("0.1");
    public static final BigDecimal CCARD_ACC_DEFAULT_CREDIT_LIMIT = new BigDecimal("100");
    public static final BigDecimal CCARD_ACC_MAX_CREDIT_LIMIT = new BigDecimal("100000");
    public static final BigDecimal SAVINGS_ACC_DEFAULT_INTEREST_RATE = new BigDecimal("0.0025");
    public static final BigDecimal SAVINGS_ACC_MAX_INTEREST_RATE = new BigDecimal("0.0025");
    public static final BigDecimal SAVINGS_ACC_DEFAULT_MIN_BALANCE = new BigDecimal("1000");
    public static final BigDecimal SAVINGS_ACC_MIN_MIN_BALANCE = new BigDecimal("100");
    public static final BigDecimal CHECKING_ACC_DEFAULT_MONTHLY_FEE = new BigDecimal("12.0");
    public static final BigDecimal CHECKING_ACC_MIN_BALANCE = new BigDecimal("250.0");
    public static final BigDecimal PENALTY_FEE = new BigDecimal("40");
}
