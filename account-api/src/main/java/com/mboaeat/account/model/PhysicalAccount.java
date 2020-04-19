package com.mboaeat.account.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "PHYSICALACCOUNT")
@Entity
@Data
@SuperBuilder
public class PhysicalAccount extends Account {

    @Embedded
    private Password password;

    public PhysicalAccount(){}
}
