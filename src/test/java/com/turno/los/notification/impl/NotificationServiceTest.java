package com.turno.los.notification.impl;

import com.turno.los.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    private MockNotificationService notificationService;

    private final Loan loan = Loan.builder()
            .loanId("L999")
            .customerName("Test User")
            .customerPhone("9876543210")
            .build();

    @Test
    void testNotifyAgentAssignmentLogsMessage() {
        assertDoesNotThrow(() -> notificationService.notifyAgentAssignment("AgentX", loan));
    }

    @Test
    void testNotifyManagerLogsMessage() {
        assertDoesNotThrow(() -> notificationService.notifyManager("ManagerY", loan));
    }

    @Test
    void testNotifyCustomerApprovalLogsMessage() {
        assertDoesNotThrow(() -> notificationService.notifyCustomerApproval("9876543210", loan));
    }
}
