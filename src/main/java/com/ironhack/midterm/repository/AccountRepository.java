package com.ironhack.midterm.repository;

import com.ironhack.midterm.dao.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByPrimaryOwnerId(Long primaryOwnerId);
    List<Account> findBySecondaryOwnersId(Long secondaryOwnerId);
//    List<Account> findByPrimaryOwnerIdOrSecondaryOwnersId(Long ownerId);
}
