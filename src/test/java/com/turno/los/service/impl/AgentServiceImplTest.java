package com.turno.los.service.impl;

import com.turno.los.model.Agent;
import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.model.enums.DecisionType;
import com.turno.los.notification.NotificationService;
import com.turno.los.repository.AgentRepository;
import com.turno.los.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgentServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AgentServiceImpl agentService;

    @Test
    void testDecideLoanApprove() {
        Agent agent = new Agent();
        agent.setId(1L);

        Loan loan = new Loan();
        loan.setLoanId("loan123");
        loan.setAssignedAgent(agent);
        loan.setCustomerPhone("9876543210");

        when(loanRepository.findById("loan123")).thenReturn(Optional.of(loan));

        String result = agentService.decideLoan(1L, "loan123", DecisionType.APPROVE);

        assertEquals("Loan decision recorded: APPROVE", result);
        verify(loanRepository).save(loan);
        verify(notificationService).notifyCustomerApproval("9876543210", loan);
    }

    @Test
    void testAssignAgentIfUnderReviewAssignsSuccessfully() {
        Loan loan = new Loan();
        loan.setApplicationStatus(ApplicationStatus.UNDER_REVIEW);

        Agent agent = new Agent();
        agent.setId(1L);
        agent.setName("Agent X");

        when(agentRepository.findAvailableAgents()).thenReturn(List.of(agent));

        agentService.assignAgentIfUnderReview(loan);

        assertEquals(agent, loan.getAssignedAgent());
        verify(loanRepository).save(loan);
        verify(notificationService).notifyAgentAssignment("Agent X", loan);
    }
}