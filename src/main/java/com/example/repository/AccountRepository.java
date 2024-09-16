package com.example.repository;

import com.example.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    // Get account from username
    public Account findByUsername(String username);

    // Get account from username and password
    public Account findByUsernameAndPassword(String username, String password);

}
