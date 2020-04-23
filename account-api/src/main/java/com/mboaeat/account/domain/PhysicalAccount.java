package com.mboaeat.account.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "PHYSICALACCOUNT")
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class PhysicalAccount extends Account {

    @Embedded
    private Password password;
}
