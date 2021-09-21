package com.ironhack.midterm.repository;

import com.ironhack.midterm.dao.user.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
    Optional<ThirdParty> findByHashedKey(String hashedKey);
}
