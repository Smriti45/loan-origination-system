package com.turno.los.notification.impl;

import com.turno.los.model.Loan;
import com.turno.los.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class MockNotificationService implements NotificationService {
    public void notifyAgentAssignment(String agentName, Loan loan) {
        log.info("Loan {} assigned to agent {}", loan.getLoanId(), agentName);
    }
    public void notifyManager(String managerName, Loan loan) {
        log.info("Notification to manager {} about loan {} assignment", managerName, loan.getLoanId());
    }
    public void notifyCustomerApproval(String phone, Loan loan) {
        log.info("Sending SMS to {} about approved/rejected loan {}", phone, loan.getLoanId());
    }
}