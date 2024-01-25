package com.example.alaa.university.jms;

import com.example.alaa.university.domain.University;
import com.example.alaa.university.exceptions.ResourceUniversityIsNotFoundException;
import com.example.alaa.university.service.IUniversityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class UniversityListener {
    private final ObjectMapper objectMapper;
    private final IUniversityService universityService;

    public UniversityListener(ObjectMapper objectMapper, IUniversityService universityService) {
        this.objectMapper = objectMapper;
        this.universityService = universityService;
    }

    @JmsListener(destination = "universities")
    public void listenUniversities(Message<String> message) {
        String payload = message.getPayload();
        System.out.println("Message from JMS " + payload);
        try {
            JmsUniversityUpdate jmsUniversityUpdate = objectMapper.readValue(payload, JmsUniversityUpdate.class);
            University existingUniversity = universityService.get(jmsUniversityUpdate.getId());
            existingUniversity.setName(jmsUniversityUpdate.getName());
            existingUniversity.setAddress(jmsUniversityUpdate.getAddress());
            existingUniversity.setUniversityType(jmsUniversityUpdate.getUniversityType());
            existingUniversity.setEmail(jmsUniversityUpdate.getEmail());
            existingUniversity.setStudyCost(jmsUniversityUpdate.getStudyCost());
            existingUniversity.setStartOperatingDate(jmsUniversityUpdate.getStartOperatingDate());
            existingUniversity.setStudents(jmsUniversityUpdate.getStudents());
            universityService.update(jmsUniversityUpdate.getId(), existingUniversity);
        } catch (ResourceUniversityIsNotFoundException e) {
            System.out.println("university id does not exist");
        } catch (JsonProcessingException e) {
            System.out.println("Message is not Json" + e.getMessage());
        }
    }
}

