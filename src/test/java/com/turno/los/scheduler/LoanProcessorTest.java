package com.turno.los.scheduler;

import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.notification.NotificationService;
import com.turno.los.repository.LoanRepository;
import com.turno.los.service.AgentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanProcessorTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private AgentService agentService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private LoanProcessor loanProcessor;

    @Test
    void testProcessLoanSetsUnderReview() throws Exception {
        Loan loan = new Loan();
        loan.setLoanId("loan123");
        loan.setLoanAmount(BigDecimal.valueOf(200000));
        loan.setApplicationStatus(ApplicationStatus.APPLIED);
        loan.setCustomerPhone("9876543210");

        doAnswer(invocation -> {
            Loan saved = invocation.getArgument(0);
            assertEquals(ApplicationStatus.UNDER_REVIEW, saved.getApplicationStatus());
            return saved;
        }).when(loanRepository).save(any(Loan.class));

        loanProcessor.processLoan(loan);

        verify(loanRepository).save(loan);
        verify(agentService).assignAgentIfUnderReview(loan);
    }

    @Test
    void testProcessLoan_ApprovedBySystem() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(15000));
        loan.setCustomerPhone("9999999999");

        loanProcessor.processLoan(loan);

        assert loan.getApplicationStatus() == ApplicationStatus.APPROVED_BY_SYSTEM;
        verify(notificationService).notifyCustomerApproval("9999999999", loan);
        verify(loanRepository).save(loan);
    }

    @Test
    void testProcessLoan_RejectedBySystem() {
        Loan loan = new Loan();
        loan.setLoanAmount(BigDecimal.valueOf(50000));
        loan.setCustomerPhone("8888888888");

        loanProcessor.processLoan(loan);

        assert loan.getApplicationStatus() == ApplicationStatus.REJECTED_BY_SYSTEM;
        verify(notificationService, never()).notifyCustomerApproval(any(), any());
        verify(loanRepository).save(loan);
    }
}

