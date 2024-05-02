package com.example.webdevelopment.controllers;

import com.example.webdevelopment.DTO.AuditEvent;
import com.example.webdevelopment.DTO.AuditEvents;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/audit")
@CrossOrigin
public class WebAudit {

    @PostMapping("/")
    public void getAuditEventDetails(@RequestBody AuditEvent auditEvent) throws JsonProcessingException {

        if (Objects.equals(auditEvent.getEventType(), "dropdown1_clicked")
                || Objects.equals(auditEvent.getEventType(), "dropdown2_clicked")
        ) {
            auditEvent.setAuditEvent(AuditEvents.DROPDOWN);
        } else if (
                Objects.equals(auditEvent.getEventType(), "button_clicked1")
                        || Objects.equals(auditEvent.getEventType(), "button_clicked2")
                        || Objects.equals(auditEvent.getEventType(), "invalid_button")
                        || Objects.equals(auditEvent.getEventType(), "button_double_clicked")
        ) {
            auditEvent.setAuditEvent(AuditEvents.BUTTON);
        } else if (Objects.equals(auditEvent.getEventType(), "input_data_entered")) {
            auditEvent.setAuditEvent(AuditEvents.INPUT);
        } else if (Objects.equals(auditEvent.getEventType(), "spent_time")) {
            auditEvent.setAuditEvent(AuditEvents.SESSION);
        } else {
            auditEvent.setAuditEvent(AuditEvents.UNKNOWN);
        }

        sendAuditEvent(auditEvent);
    }

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    private KafkaTemplate<Object, String> kafkaTemplate;

    public void sendAuditEvent(AuditEvent auditEvent) throws JsonProcessingException {
        String auditEventJson = objectMapper.writeValueAsString(auditEvent);
        kafkaTemplate.send("audit_event", auditEventJson);
    }


}
