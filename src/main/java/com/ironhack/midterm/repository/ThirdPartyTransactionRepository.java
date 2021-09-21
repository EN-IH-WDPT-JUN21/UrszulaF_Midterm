package com.ironhack.midterm.repository;

import com.ironhack.midterm.dao.account.ThirdPartyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyTransactionRepository extends JpaRepository<ThirdPartyTransaction, Long> {
}
