package com.mboaeat.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MOBILE_TOKENS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileAuthentication implements Serializable {

    @Id
    @Column(name = "MOBILE_AUTH_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorMobileAuth")
    @SequenceGenerator(name = "idGeneratorMobileAuth", sequenceName = "SEQ_MOBILE_AUTH", allocationSize = 100)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "TOKEN", length = 8)
    private String token;

}
