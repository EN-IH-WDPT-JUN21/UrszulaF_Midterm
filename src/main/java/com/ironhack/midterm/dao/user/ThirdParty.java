package com.ironhack.midterm.dao.user;

import com.ironhack.midterm.dao.account.ThirdPartyTransaction;
import com.ironhack.midterm.dao.account.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ThirdParty{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashedKey;

    private String name;

    @OneToMany(mappedBy = "thirdParty", cascade = CascadeType.ALL)
    private List<ThirdPartyTransaction> transactionsSend;
}
