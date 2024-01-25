package com.example.alaa.university.jms;

import com.example.alaa.university.domain.Teacher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsTeacherSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public JmsTeacherSender(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTeacherAddedMessage(Teacher teacher) {
        try {
            jmsTemplate.convertAndSend("teacher-notifier-added",
                    objectMapper.writeValueAsString(teacher));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

