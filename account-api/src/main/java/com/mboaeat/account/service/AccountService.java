package com.mboaeat.account.service;

import com.mboaeat.account.domain.*;
import com.mboaeat.account.repository.AccountRepository;
import com.mboaeat.common.domain.EmailAddress;
import com.mboaeat.common.dto.ErrorCodeConstants;
import com.mboaeat.common.dto.User;
import com.mboaeat.common.exception.MboaEatException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.mboaeat.account.service.ServiceUtils.toUserDto;
import static com.mboaeat.common.PasswordGenerator.isMatches;
import static com.mboaeat.common.PasswordGenerator.passwd;
import static com.mboaeat.common.RandomPasswordGenerator.generatePswd;

@Service
@Transactional(readOnly = true)
public class AccountService {

    final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(String email, String password) throws AccountExistException {
        accountExist(email);
        String hash = generatePswd();
        Account account = PhysicalAccount.builder()
                .password(
                        Password.builder()
                                .value(passwd(hash, password))
                                .hash(hash)
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

    /**
     * Get account by email and password. Login process
     * @param email
     * @param password
     * @return Account object
     * @throws AccountNotFoundException No account found
     */
    public Account getByEmailAndPassword(String email, String password) throws AccountNotFoundException {
        PhysicalAccount account = getAccountByEmail(email);

        if (!isMatches(password, account.getPassword().getHash(), account.getPassword().getValue())){
            throw new AccountNotFoundException("No account found with email and password", ErrorCodeConstants.AC1000);
        }

        return account;
    }

    /**
     * Get aacount by email
     * @param email
     * @return Account object
     * @throws AccountNotFoundException
     */
    public PhysicalAccount getAccountByEmail(String email) throws AccountNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByEmailAddress(
                EmailAddress.builder()
                        .value(email)
                        .build()
        );

        return (PhysicalAccount) optionalAccount.orElseThrow(() -> new AccountNotFoundException("No account found with email " + email, ErrorCodeConstants.AC1000));
    }

    public User getUserByEmail(String email) {
        return toUserDto(getAccountByEmail(email));
    }

    /**
     * manage account
     *
     * @param id
     * @param email
     * @param name
     * @param firstName
     * @param middleName
     * @return
     */
    public Account updateAccount(
            Long id,
            String email,
            String name,
            String firstName,
            String middleName
    ) throws MboaEatException {
        PhysicalAccount account = (PhysicalAccount) getAccountById(id);
        accountExist(account, email);
        if (StringUtils.isNotEmpty(email)) {
            account.getNaturalPerson()
                    .setEmailAddress(
                            EmailAddress
                                    .builder()
                                    .value(email)
                                    .build()
                    );
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
        return accountRepository.saveAndFlush(account);
    }

    public void updatePassword(Long id, String password, String newPassword, String confirmPassword) {
        PhysicalAccount account = (PhysicalAccount) getAccountById(id);
        if (isNotEmptyPassword(password, newPassword, confirmPassword)){
            if (isMatches(password, account.getPassword().getHash(), account.getPassword().getValue())){
                if ( !isMatches(newPassword, account.getPassword().getHash(), account.getPassword().getValue()) && newPassword.equals(confirmPassword)){
                    String hash = generatePswd();
                    account.setPassword(
                            Password.builder()
                                    .value(passwd(hash, password))
                                    .hash(hash)
                                    .build()
                    );
                } else {
                    throw new InsufficientPasswordException("Account password not sufficiently trusted");
                }
            } else {
                throw new InsufficientPasswordException("Account password not sufficiently trusted");
            }
        }
    }

    public Account getAccountById(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(
                () ->
                    new AccountNotFoundException("Account not exist with id " + id, ErrorCodeConstants.AC1000)
        );
    }

    private boolean isNotEmptyPassword(String ... passwords) {
        for (String pwd: passwords){
            if ( StringUtils.isEmpty(pwd) ){
                return false;
            }
        }
        return true;
    }


    private void accountExist(PhysicalAccount account, String email) {
        if (account.getNaturalPerson().getEmailAddress().getValue().equalsIgnoreCase(email)){
            return;
        }
        accountExist(email);
    }

    private void accountExist(String email) throws AccountExistException {
        if (existsAccountByEmail(email)){
            throw new AccountExistException("Exist account with email " + email, ErrorCodeConstants.AC1001);
        }
    }

    public boolean existsAccountByEmail(String email) throws AccountExistException {
        Optional<Account> account = accountRepository.findByEmailAddress(
                EmailAddress.builder()
                        .value(email)
                        .build()
        );

        if (account.isPresent()){
          return true;
        }

        return false;
    }
}
