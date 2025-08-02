package com.turno.los.scheduler;

import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.repository.LoanRepository;
import com.turno.los.service.AgentService;
import com.turno.los.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class LoanProcessor {
    public LoanProcessor(LoanRepository loanRepository, AgentService agentService, NotificationService notificationService) {
        this.loanRepository = loanRepository;
        this.agentService = agentService;
        this.notificationService = notificationService;
    }

    private final LoanRepository loanRepository;
    private final AgentService agentService;
    private final NotificationService notificationService;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final Random random = new Random();

    @Scheduled(fixedRate = 30000)
    public void processLoans() {
        List<Loan> loans = loanRepository.findByApplicationStatus(ApplicationStatus.APPLIED);
        for (Loan loan : loans) {
            executor.submit(() -> processLoan(loan));
        }
    }

    public void processLoan(Loan loan) {
        try {
            Thread.sleep(5000 + random.nextInt(20000));

            if (loan.getLoanAmount().compareTo(BigDecimal.valueOf(100000)) > 0) {
                loan.setApplicationStatus(ApplicationStatus.UNDER_REVIEW);
                loanRepository.save(loan);
                agentService.assignAgentIfUnderReview(loan);
                return;
            }

            if (loan.getLoanAmount().compareTo(BigDecimal.valueOf(20000)) < 0) {
                loan.setApplicationStatus(ApplicationStatus.APPROVED_BY_SYSTEM);
                notificationService.notifyCustomerApproval(loan.getCustomerPhone(), loan);
            } else {
                loan.setApplicationStatus(ApplicationStatus.REJECTED_BY_SYSTEM);
            }

            loanRepository.save(loan);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
