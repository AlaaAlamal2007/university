package com.example.alaa.university.jms;

import com.example.alaa.university.domain.University;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsUniversitySender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public JmsUniversitySender(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUniversityAddedMessage(University university) {
        try {
            jmsTemplate.convertAndSend("university-notifier-added",
                    objectMapper.writeValueAsString(university));
        } catch (JsonProcessingException e) {
            System.out.println("something was wrong");
        }
    }
}

