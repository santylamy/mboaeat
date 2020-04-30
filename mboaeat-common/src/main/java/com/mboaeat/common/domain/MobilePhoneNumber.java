package com.mboaeat.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MobilePhoneNumber {

    @Column(name = "MOBILE_PHONE_NUMBER")
    private String value;

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
        MobilePhoneNumber other = (MobilePhoneNumber) obj;
        return new EqualsBuilder().append(value, other.value).isEquals();
    }
}
