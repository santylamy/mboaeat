package com.mboaeat.account.controller;

import com.mboaeat.account.model.Account;
import com.mboaeat.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/account/api/v1")
@Validated
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public Account add(@Email @RequestParam(value = "email", required = true) String email, @RequestParam(value = "password", required = true) String password){
        return accountService.createAccount(email, password);
    }

    @PutMapping("/{id}")
    public Account update(@PathVariable( value = "id", required = true) Long id,
                          @Email @RequestParam("email") String email,
                          @RequestParam("passwordCurrent") String password,
                          @RequestParam("newPassword") String newPassword,
                          @RequestParam("confirmPassword") String confirmPassword,
                          @RequestParam("name") String name,
                          @RequestParam("firstName") String firstName,
                          @RequestParam("middleName") String middleName,
                          @RequestParam("customer") Long customer
                          ) {

        return accountService.updateAccount(id,email,password,newPassword,confirmPassword,name,firstName,middleName, customer);
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable("id") Long id) {
        return accountService.getAccountById(id);
    }

}
