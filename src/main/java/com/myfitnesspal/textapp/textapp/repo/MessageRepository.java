package com.myfitnesspal.textapp.textapp.repo;

import com.myfitnesspal.textapp.textapp.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, Long> {

    Messages save(Messages messages);

    Messages findMessagesById(long id);

    List<Messages> findByUsername(String username);

}
