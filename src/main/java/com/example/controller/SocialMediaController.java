package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {

        // Check account exists
        if (accountService.accountExistsWithUsername(account.getUsername()))
            return ResponseEntity.status(409).body(account);

        // Create new account
        var newAccount = accountService.insertAccount(account);
        if (newAccount == null)
            return ResponseEntity.status(400).body(account);
        return ResponseEntity.status(200).body(newAccount);
    }

    // Account login
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {

        // Authenticate account
        var authenticatedAccount = accountService.loginAccount(account);
        if (authenticatedAccount == null)
            return ResponseEntity.status(401).body(account);
        return ResponseEntity.status(200).body(authenticatedAccount);
    }

    // Create new message
    @PostMapping("/messages")
    public ResponseEntity<Message> login(@RequestBody Message message) {
        var newMessage = messageService.createMessage(message);
        if (newMessage == null)
            return ResponseEntity.status(400).body(message);
        return ResponseEntity.status(200).body(newMessage);
    }

    // Get all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> listMessages() {
        var messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    // Get message by id
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int messageId) {
        var message = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(message);
    }

    // Delete message by id
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable int messageId) {
        var deletedMessage = messageService.deleteMessageById(messageId);
        if (deletedMessage == null)
            return ResponseEntity.status(200).body("");
        return ResponseEntity.status(200).body("1");
    }

    // Update message
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        var updatedMessage = messageService.updateMessage(messageId, message);
        if (updatedMessage == null)
            return ResponseEntity.status(400).body("");
        return ResponseEntity.status(200).body("1");
    }
    
    // Get all messages by user
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getAccountMessages(@PathVariable int accountId) {
        var messages = messageService.getAllMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(messages);
    }
}
