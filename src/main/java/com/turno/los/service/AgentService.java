package com.turno.los.service;

import com.turno.los.model.Loan;
import com.turno.los.model.enums.DecisionType;

public interface AgentService {
    String decideLoan(Long agentId, String loanId, DecisionType decision);
    void assignAgentIfUnderReview(Loan loan);
}
