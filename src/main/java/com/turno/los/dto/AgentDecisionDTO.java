package com.turno.los.dto;

import com.turno.los.model.enums.DecisionType;
import lombok.Data;

@Data
public class AgentDecisionDTO {
    private DecisionType decision;
}
