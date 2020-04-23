package com.mboaeat.account.domain;

import lombok.Builder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static org.apache.commons.lang.StringUtils.upperCase;

@Embeddable
@Builder
public class PersonName {

    private static final String SPACE = " ";

    @Column(name = "NAME")
    private String name;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    public PersonName(){}

    public PersonName(String lastName, String firstName) {
        this(lastName, firstName, null);
    }

    public PersonName(String lastName, String firstName, String middleName) {

        this.name = lastName;
        this.firstName = StringUtils.isBlank(firstName) ? null : firstName;
        ;
        this.middleName = StringUtils.isBlank(middleName) ? null : middleName;
        ;
    }

    public PersonName(String lastName) {
        this(lastName, null);
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public boolean hasFirstName() {
        return !StringUtils.isEmpty(firstName);
    }

    public boolean hasMiddleName() {
        return !StringUtils.isEmpty(middleName);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(upperCase(name))
                .append(upperCase(firstName))
                .append(upperCase(middleName))
                .toHashCode();
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
        PersonName other = (PersonName) obj;
        return new EqualsBuilder()
                .append(upperCase(name), upperCase(other.name))
                .append(upperCase(firstName), upperCase(other.firstName))
                .append(upperCase(middleName), upperCase(other.middleName))
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(name).append(firstName).append(middleName).toString();
    }

    public String getDisplayFirstName() {
        if (hasFirstName()) {
            return firstName;
        } else {
            return "*";
        }
    }

    public String getDisplayCompleteFirstName() {
        if (hasFirstName() && !hasMiddleName()) {
            return firstName;
        }
        if (hasFirstName() && hasMiddleName()) {
            return firstName + SPACE + middleName;
        } else {
            return "*";
        }
    }

    public String getFullName() {
        return getName() + ", " + getDisplayFirstName();
    }

    public String getFullNameWithMiddleName() {
        return getName() + ", " + getDisplayCompleteFirstName();
    }
}
