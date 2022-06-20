package com.myfitnesspal.textapp.textapp;

import com.myfitnesspal.textapp.textapp.model.Messages;
import com.myfitnesspal.textapp.textapp.repo.MessageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
public class MessageRepositoryTests {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void saveMessageTest(){
        Messages message = Messages.builder()
                .id(1l)
                .username("testme")
                .text("testMessage")
                .timeout(10L)
                .expirationDate(LocalDateTime.now())
                .wasRead(false).build();
        messageRepository.save(message);
        Assertions.assertThat(message.getId()).isGreaterThan(0);
    }

    @Test
    public void findMessageByIdTest(){
        Messages message = Messages.builder()
                .id(1l)
                .username("testme")
                .text("testMessage")
                .timeout(10L)
                .expirationDate(LocalDateTime.now())
                .wasRead(false).build();
        messageRepository.save(message);
        Assertions.assertThat(message.getId()).isEqualTo(1l);
    }

    @Test
    public void findMessagesByUsernameTest(){
        Messages message = Messages.builder()
                .id(1l)
                .username("testme")
                .text("testMessage")
                .timeout(10L)
                .expirationDate(LocalDateTime.now())
                .wasRead(false).build();
        messageRepository.save(message);
        Messages message2 = Messages.builder()
                .id(2l)
                .username("testme")
                .text("testMessage2")
                .timeout(10L)
                .expirationDate(LocalDateTime.now())
                .wasRead(false).build();
        messageRepository.save(message2);

        List<Messages> messagesList = messageRepository.findByUsername("testme");
       Assertions.assertThat(messagesList.size()).isGreaterThan(0);
        Assertions.assertThat(messagesList.size()).isLessThan(3);
    }

}
