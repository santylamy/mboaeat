package com.mboaeat.account.service;

import com.mboaeat.account.domain.Account;
import com.mboaeat.account.domain.NaturalPerson;
import com.mboaeat.account.domain.PersonName;
import com.mboaeat.common.dto.User;
import com.mboaeat.common.dto.User.UserBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility methods for JPA services
 */
public final class ServiceUtils {

    static User toUserDto(final Account account) {
        final UserBuilder builder = User.builder();
            builder
                    .email(account.getNaturalPerson().getEmailAddress().getValue())
                    .firstName(account.getNaturalPerson().getPersonName() != null ? account.getNaturalPerson().getPersonName().getName() : null)
                    .lastName(account.getNaturalPerson().getPersonName() != null ? account.getNaturalPerson().getPersonName().getFirstName() : null)
                    .middleName(toMiddleName(account.getNaturalPerson()))
                    .id(String.valueOf(account.getId()));
        return builder.build();
    }

    static String toMiddleName(NaturalPerson naturalPerson){

        if (naturalPerson.getPersonName() == null){
            return defaultMiddleName(naturalPerson);
        }

        if (StringUtils.isEmpty(naturalPerson.getPersonName().getMiddleName())){
            return defaultMiddleName(naturalPerson);
        }


        return naturalPerson.getPersonName().getMiddleName();
    }

    private static String defaultMiddleName(NaturalPerson naturalPerson) {
        int splitPosition = naturalPerson.getEmailAddress().getValue().lastIndexOf('@');
        return naturalPerson.getEmailAddress().getValue().substring(0, splitPosition);
    }

}
