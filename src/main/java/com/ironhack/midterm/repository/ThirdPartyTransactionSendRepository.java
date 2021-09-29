package com.ironhack.midterm.repository;

import com.ironhack.midterm.dao.account.ThirdPartyTransactionSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThirdPartyTransactionSendRepository extends JpaRepository<ThirdPartyTransactionSend, Long> {
    List<ThirdPartyTransactionSend>  findBySenderThirdPartyHashedKey(String hashedKey);
}
