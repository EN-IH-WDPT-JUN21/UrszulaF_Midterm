package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.dao.account.Transaction;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AccountDTO {

    @Digits(integer = 19, fraction = 2, message = "Maximum balance amount is 19, no decimals.")
    private String balanceAmount;

    private String balanceCurrency;

    private String secretKey;

    @Column(updatable=false)
    @CreationTimestamp
    private String creationDate;

    private String statusString;

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Status getStatus() {
        return Status.valueOf(statusString);
    }

    public void setStatus(Status status) {
        this.statusString = status.toString();
    }
}
