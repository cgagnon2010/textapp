package com.myfitnesspal.textapp.textapp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue()
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "text_message", nullable = false)
    private String textMessage;

    @Column(name = "expiration", nullable = false)
    private Integer messageExpiration ;
}
