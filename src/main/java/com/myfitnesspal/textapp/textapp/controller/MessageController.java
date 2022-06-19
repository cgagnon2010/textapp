package com.myfitnesspal.textapp.textapp.controller;

import com.fasterxml.jackson.annotation.JsonView;

import com.myfitnesspal.textapp.textapp.model.MessageViews;
import com.myfitnesspal.textapp.textapp.model.Messages;
import com.myfitnesspal.textapp.textapp.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


//TODO write all test cases
//TODO write error handlers as needed
@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/chats")
    @JsonView(MessageViews.PostReturn.class)
    public ResponseEntity<Messages> sendNewMessage(@RequestBody Messages messages){
        Messages entity = messages;
        LocalDateTime now = LocalDateTime.now().plusSeconds(entity.getTimeout());
//        LocalDateTime now = LocalDateTime.now().plusMinutes(entity.getTimeout());
//        use the above commented out code during testing for visibility of timeout
        entity.setExpirationDate(now);
        try{
            return new ResponseEntity<Messages>(messageRepository.save(entity), HttpStatus.CREATED);
        }catch (Exception e){


        }
        return null;
    }

    @GetMapping("/chat/{id}")
    @JsonView(MessageViews.GetById.class)
    public ResponseEntity<Messages> findMessagesById(@PathVariable(value = "id") long messageId){
        return new ResponseEntity<Messages>(messageRepository.findMessagesById(messageId), HttpStatus.OK);
    }

    @GetMapping("/chats/{username}")
    @JsonView(MessageViews.GetByUsername.class)
    public ResponseEntity<List<Messages>> findByUsername(@PathVariable(value = "username") String username){
        List<Messages> messages = messageRepository.findByUsername(username);

        List<Messages> notExpiredMessages = new ArrayList<>();
        for (Messages mess : messages) {
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(mess.getExpirationDate())  && mess.isWasRead() == false){
                notExpiredMessages.add(mess);
            }
        }
        for (Messages putMessage : notExpiredMessages) {
            putMessage.setWasRead(true);
//            putMessage.setExpirationDate(LocalDateTime.now());
//            is there a reason a message would go unread and we would need to flip it to read after its reached its expired datetime?
            messageRepository.save(putMessage);
        }
        return new ResponseEntity<List<Messages>>(notExpiredMessages, HttpStatus.OK);
    }
}
