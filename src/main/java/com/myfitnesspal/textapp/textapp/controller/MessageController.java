package com.myfitnesspal.textapp.textapp.controller;

import com.myfitnesspal.textapp.textapp.model.Messages;
import com.myfitnesspal.textapp.textapp.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//TODO Figure out custom responses and write up documentation IE paths and acceptable arguments
//TODO Figure out how to implement the timeout system
//TODO figure out how to default the timeout value so it cant be passed as something else.
//TODO write all test cases
//TODO write error handlers as needed
@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    //TODO write up the documentation on the API request and responses
    @PostMapping("/chats")
    public ResponseEntity<Messages> sendNewMessage(@RequestBody Messages messages){
        return new ResponseEntity<Messages>(messageRepository.save(messages), HttpStatus.CREATED);
    }

    //TODO write up the documentation on the API request and responses
    @GetMapping("/chat/{id}")
    public ResponseEntity<Messages> findMessagesById(@PathVariable(value = "id") long messageId){
        return new ResponseEntity<Messages>(messageRepository.findMessagesById(messageId), HttpStatus.OK);
    }

    //TODO Try to re write this query to a similar format as the others
    //TODO write up the documentation on the API request and responses
    @GetMapping("/chats/{username}")
    public List<Messages> findByUsername(@PathVariable(value = "username") String username){
        List<Messages> messages = messageRepository.findByUsername(username);
        return messages;
    }


}
