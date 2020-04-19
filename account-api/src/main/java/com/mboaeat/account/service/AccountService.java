package com.mboaeat.account.service;

import com.mboaeat.account.model.*;
import com.mboaeat.account.repository.AccountRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("secret", 10000, 128);

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    public Account createAccount(String email, String password) {
        Account account = PhysicalAccount.builder()
                .password(
                        Password.builder()
                                .value(encodePassword(password))
                                .build()
                )
                .naturalPerson(
                        NaturalPerson
                                .builder()
                                .emailAddress(
                                        EmailAddress
                                                .builder()
                                                .value(email)
                                                .build()
                                )
                                .build()
                )
                .build();
        return accountRepository.save(account);
    }

    public Account updateAccount(
            Long id,
            String email,
            String password,
            String newPassword,
            String confirmPassword,
            String name,
            String firstName,
            String middleName,
            Long customer
    ) {
        PhysicalAccount account = (PhysicalAccount) getAccountById(id);
        if (StringUtils.isNotEmpty(email)) {
            account.getNaturalPerson()
                    .setEmailAddress(
                            EmailAddress
                                    .builder()
                                    .value(email)
                                    .build()
                    );
        }
        if (isNotEmptyPassword(password, newPassword, confirmPassword)){
            if (encoder.matches(password, account.getPassword().getValue())){
                if ( !encoder.matches(newPassword, account.getPassword().getValue()) && newPassword.equals(confirmPassword)){
                    account.setPassword(
                            Password.builder()
                                    .value(encodePassword(newPassword))
                                    .build()
                    );
                }
            }
        }
        if (StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(firstName) || StringUtils.isNotEmpty(middleName)){
            account.setNaturalPerson(
                    NaturalPerson
                            .builder()
                            .personName(
                                    PersonName
                                            .builder()
                                            .name(name)
                                            .firstName(firstName)
                                            .middleName(middleName)
                                            .build()
                            )
                            .build()
            );
        }
        if (customer != null){
            account.setCustomerId(customer);
        }
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.getOne(id);
    }

    private boolean isNotEmptyPassword(String ... passwords) {
        for (String pwd: passwords){
            if ( StringUtils.isEmpty(pwd) ){
                return false;
            }
        }
        return true;
    }

    private String encodePassword(String password){
        return encoder.encode(password);
    }

}
