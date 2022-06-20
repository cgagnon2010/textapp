package com.myfitnesspal.textapp.textapp.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue()
    @JsonView({MessageViews.PostReturn.class, MessageViews.GetByUsername.class})
    private Long id;

    @JsonView(MessageViews.GetById.class)
    @Column(name = "username", nullable = false)
    private String username;

    @JsonView({MessageViews.GetById.class, MessageViews.GetByUsername.class})
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "timeout", columnDefinition = "long default 10")
    private long timeout;

    @JsonView(MessageViews.GetById.class)
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "was_read", columnDefinition = "boolean default false")
    private boolean wasRead = false;
}
