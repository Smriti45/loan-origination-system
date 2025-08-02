package com.turno.los.controller;

import com.turno.los.dto.AgentDecisionDTO;
import com.turno.los.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    private final AgentService agentService;
    @PutMapping("/{agentId}/loans/{loanId}/decision")
    public ResponseEntity<String> makeDecision(@PathVariable Long agentId,
                                             @PathVariable String loanId,
                                             @RequestBody AgentDecisionDTO decisionDTO) {
        return ResponseEntity.ok(agentService.decideLoan(agentId, loanId, decisionDTO.getDecision()));
    }
}