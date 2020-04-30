package com.mboaeat.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ClientName {

    @Column(name = "CLIENT_NAME")
    private String name;
}
