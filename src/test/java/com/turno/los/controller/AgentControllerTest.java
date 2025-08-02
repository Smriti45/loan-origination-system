package com.turno.los.controller;

import com.turno.los.dto.AgentDecisionDTO;
import com.turno.los.model.enums.DecisionType;
import com.turno.los.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgentControllerTest {

    @Mock
    private AgentService agentService;

    @InjectMocks
    private AgentController agentController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakeDecision() {
        // Arrange
        Long agentId = 1L;
        String loanId = "loan123";
        AgentDecisionDTO dto = new AgentDecisionDTO();
        dto.setDecision(DecisionType.APPROVE);

        String expected = "Loan decision recorded: APPROVE";
        when(agentService.decideLoan(agentId, loanId, DecisionType.APPROVE)).thenReturn(expected);

        // Act
        ResponseEntity<String> response = agentController.makeDecision(agentId, loanId, dto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(agentService, times(1)).decideLoan(agentId, loanId, DecisionType.APPROVE);
    }
}
