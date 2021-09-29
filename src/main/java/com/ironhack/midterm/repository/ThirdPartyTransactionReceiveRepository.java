package com.ironhack.midterm.repository;

import com.ironhack.midterm.dao.account.ThirdPartyTransactionReceive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThirdPartyTransactionReceiveRepository extends JpaRepository<ThirdPartyTransactionReceive, Long> {
    List<ThirdPartyTransactionReceive> findByRecipientThirdPartyHashedKey(String hashedKey);
}
