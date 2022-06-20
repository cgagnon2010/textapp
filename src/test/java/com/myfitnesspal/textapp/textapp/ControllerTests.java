package com.myfitnesspal.textapp.textapp;

import com.myfitnesspal.textapp.textapp.controller.MessageController;
import com.myfitnesspal.textapp.textapp.model.Messages;
import com.myfitnesspal.textapp.textapp.repo.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ControllerTests {

    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    MessageController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sendNewMessageTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Messages message = Messages.builder()
                .id(1l)
                .username("testme")
                .text("testMessage")
                .timeout(10L)
                .expirationDate(LocalDateTime.now())
                .wasRead(false).build();

        ResponseEntity<Messages> responseEntity = controller.sendNewMessage(message);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void findMessagesByIdTests(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Messages message = Messages.builder()
                .id(1l)
                .username("testme")
                .text("testMessage")
                .timeout(10L)
                .expirationDate(LocalDateTime.now())
                .wasRead(false).build();
        ResponseEntity<Messages> responseEntity = controller.findMessagesById(1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    public void findByUsernameTest() throws  Exception{
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
        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.get("/api/chats/testme"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
