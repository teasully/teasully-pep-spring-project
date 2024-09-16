package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // Get all message
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Get a message by id
    public Message getMessageById(int messageId) {
        var message = messageRepository.findById(messageId);
        if (message.isEmpty())
            return null;
        return message.get();
    }

    // Get a message by account id
    public List<Message> getAllMessagesByAccountId(int accountId) {
        var messages = messageRepository.findByPostedBy(accountId);
        return messages;
    }

    // Insert a new message
    public Message createMessage(Message message) {

        // Sanitize input
        if (!ValidateMessage(message))
            return null;

        // Check valid user posting message
        var optionalAccount = accountRepository.findById(message.getPostedBy());
        if (!optionalAccount.isPresent())
            return null;

        // Create new message
        return messageRepository.save(message);
    }

    // Delete a message by id
    public Message deleteMessageById(int messageId) {

        // Check message exists
        var optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isEmpty())
            return null;

        // Delete message
        messageRepository.deleteById(messageId);
        return optionalMessage.get();
    }

    // Update message
    public Message updateMessage(int messageId, Message replacement) {

        // Sanitize input
        if (!ValidateMessage(replacement))
            return null;

        // Check message exists
        var optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isEmpty())
            return null;

        // Update message
        var message = optionalMessage.get();
        message.setMessageText(replacement.getMessageText());

        messageRepository.save(message);
        return message;
    }

    //
    static boolean ValidateMessage(Message message) {
        var messageText = message.getMessageText();
        if (messageText.length() == 0 || messageText.length() > 255)
            return false;
        return true;
    }
}
