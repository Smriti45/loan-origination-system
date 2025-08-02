package com.turno.los.service.impl;

import com.turno.los.model.Agent;
import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.model.enums.DecisionType;
import com.turno.los.repository.AgentRepository;
import com.turno.los.repository.LoanRepository;
import com.turno.los.service.AgentService;
import com.turno.los.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentServiceImpl implements AgentService {
    private final LoanRepository loanRepository;
    private final AgentRepository agentRepository;
    private final NotificationService notificationService;
    @Override
    public void assignAgentIfUnderReview(Loan loan) {
        if (loan.getApplicationStatus() == ApplicationStatus.UNDER_REVIEW && loan.getAssignedAgent() == null) {
            List<Agent> available = agentRepository.findAvailableAgents();
            if (!available.isEmpty()) {
                Agent agent = available.get(0);
                loan.setAssignedAgent(agent);
                loanRepository.save(loan);

                notificationService.notifyAgentAssignment(agent.getName(), loan);

                if (agent.getManager() != null) {
                    notificationService.notifyManager(agent.getManager().getName(), loan);
                }
            }
        }
    }

    @Override
    public String decideLoan(Long agentId, String loanId, DecisionType decision) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getAssignedAgent() == null || !loan.getAssignedAgent().getId().equals(agentId)) {
            throw new IllegalArgumentException("Loan not assigned to this agent");
        }

        switch (decision) {
            case APPROVE -> loan.setApplicationStatus(ApplicationStatus.APPROVED_BY_AGENT);
            case REJECT -> loan.setApplicationStatus(ApplicationStatus.REJECTED_BY_AGENT);
        }

        loanRepository.save(loan);
        notificationService.notifyCustomerApproval(loan.getCustomerPhone(), loan);

        return "Loan decision recorded: " + decision;
    }
}

