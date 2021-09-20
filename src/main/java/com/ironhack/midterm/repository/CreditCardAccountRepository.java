package com.ironhack.midterm.repository;

import com.ironhack.midterm.dao.CreditCardAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardAccountRepository extends JpaRepository<CreditCardAccount, Long> {

//    applyInterestRate(creditCard);
//checkFraud(creditCard);
}
