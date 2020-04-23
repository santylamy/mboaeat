package com.mboaeat.account.service;

import com.mboaeat.account.domain.Account;
import com.mboaeat.common.dto.User;
import com.mboaeat.common.dto.User.UserBuilder;

/**
 * Utility methods for JPA services
 */
public final class ServiceUtils {

    static User toUserDto(final Account account) {
        final UserBuilder builder = User.builder();
            builder
                    .email(account.getNaturalPerson().getEmailAddress().getValue())
                    .firstName(account.getNaturalPerson().getPersonName().getName())
                    .lastName(account.getNaturalPerson().getPersonName().getFirstName())
                    .middleName(account.getNaturalPerson().getPersonName().getMiddleName());
        return builder.build();
    }

}
