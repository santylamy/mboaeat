package com.mboaeat.account.service;

import com.mboaeat.account.domain.MobileAuthentication;

public interface AuthenticationService {

    MobileAuthentication getOrCreateMobileAuthentication();
    MobileAuthentication updateMobileAuthentication();

}
