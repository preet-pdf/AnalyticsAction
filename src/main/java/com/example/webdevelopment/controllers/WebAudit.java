package com.example.webdevelopment.controllers;

import com.example.webdevelopment.DTO.AlertDTO;
import com.example.webdevelopment.DTO.AuditEvent;
import com.example.webdevelopment.DTO.AuditEvents;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/audit")
@CrossOrigin
public class WebAudit {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAudit.class);


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

    private static List<AlertDTO> alertDTOList = new ArrayList<>();

    public void sendAuditEvent(AuditEvent auditEvent) throws JsonProcessingException {
        String auditEventJson = objectMapper.writeValueAsString(auditEvent);
        kafkaTemplate.send("audit_event", auditEventJson);
    }

    @KafkaListener(topics = "alert", groupId = "alert")
    public void auditConsumer(String alert) throws JsonProcessingException {
        AlertDTO alertDTO = objectMapper.readValue(alert, AlertDTO.class);
        alertDTOList.add(alertDTO);
    }

    @GetMapping
    public List<AlertDTO> getAlertDTOList() {
        return alertDTOList;
    }

}
