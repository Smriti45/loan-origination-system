package com.turno.los.notification;

import com.turno.los.model.Loan;

public interface NotificationService {
    void notifyAgentAssignment(String agentName, Loan loan);
    void notifyCustomerApproval(String phone, Loan loan);
    void notifyManager(String managerName, Loan loan);
}
