package com.ironhack.midterm.dao.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ironhack.midterm.dao.account.ThirdPartyTransactionSend;
import com.ironhack.midterm.dao.account.ThirdPartyTransactionReceive;
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

    @OneToMany(mappedBy = "senderThirdParty", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<ThirdPartyTransactionSend> thirdPartyTransactionsSend;

    @OneToMany(mappedBy = "recipientThirdParty", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<ThirdPartyTransactionReceive> thirdPartyTransactionReceived;

    public ThirdParty(String hashedKey, String name) {
        this.hashedKey = hashedKey;
        this.name = name;
    }
}
