package com.ironhack.midterm.controller.dto;

import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.utils.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountDTO{

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

    @Digits(integer = 19, fraction = 2, message = "Maximum balance amount is 19, no decimals.")
    private String  minimumBalanceAmount;

    private String  minimumBalanceCurrency;

    @DecimalMax(value="0.50")
    @Digits(integer=1, fraction=4)
    private String interestRate;

    @UpdateTimestamp
    private LocalDateTime lastInterestApplied;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",column=@Column(name="penalty_fee_amount")),
            @AttributeOverride(name="currency",column=@Column(name="penalty_fee_currency"))
    })
    private Money penaltyFee;
}
