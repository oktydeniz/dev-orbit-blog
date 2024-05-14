package com.odeniz.dev.orbit.service;


import com.odeniz.dev.orbit.entity.Message;
import com.odeniz.dev.orbit.model.MessageRequest;
import com.odeniz.dev.orbit.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public boolean save(MessageRequest messageRequest) {
        try {
            Message message = new Message();
            message.setAbout(messageRequest.getAbout());
            message.setFullName(messageRequest.getFullName());
            message.setContent(messageRequest.getContent());
            message.setMail(messageRequest.getMail());
            messageRepository.save(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message findById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }
}
