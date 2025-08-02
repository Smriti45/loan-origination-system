package com.turno.los.dto;

import com.turno.los.model.enums.DecisionType;
import lombok.Data;

@Data
public class AgentDecisionDTO {
    public DecisionType getDecision() {
        return decision;
    }

    public void setDecision(DecisionType decision) {
        this.decision = decision;
    }

    private DecisionType decision;
}
