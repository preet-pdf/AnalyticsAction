package com.example.webdevelopment.DTO;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AlertDTO {
    private AuditEvent auditEvent;
    private String ruleName;
    private String ruleDescription;
    private long createdTime;
}
