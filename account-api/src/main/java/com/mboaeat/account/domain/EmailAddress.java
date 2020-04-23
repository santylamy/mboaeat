package com.mboaeat.account.domain;

import lombok.Builder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
public class EmailAddress {

    @Column(name = "EMAIL")
    private String value;

    public EmailAddress() {
    }

    public EmailAddress(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EmailAddress other = (EmailAddress) obj;
        return new EqualsBuilder().append(value, other.value).isEquals();
    }

}
