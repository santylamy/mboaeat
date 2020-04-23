package com.mboaeat.account.repository;

import com.mboaeat.account.domain.Account;
import com.mboaeat.account.domain.MobileAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MobileAuthenticationRepository extends JpaRepository<MobileAuthentication, Long> {

    @Query("SELECT auth FROM MobileAuthentication auth where auth.account = :account and auth.token = :token")
    public MobileAuthentication validateAuthenticationToken(@Param("account") Account account, @Param("token") String token);

    public MobileAuthentication findByAccount(Account account);
}
