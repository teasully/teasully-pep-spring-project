package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Get an existing account
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public boolean accountExistsWithUsername(String username) {
        return getAccountByUsername(username) != null;
    }

    // Create new account
    public Account insertAccount(Account account) {

        // Sanitize input
        if (account.getUsername().length() == 0)
            return null;
        if (account.getPassword().length() < 4)
            return null;

        // Create account
        return accountRepository.save(account);
    }

    // Authenticate username and password
    public Account loginAccount(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

}
