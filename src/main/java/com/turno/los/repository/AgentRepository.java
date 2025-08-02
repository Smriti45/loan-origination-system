package com.turno.los.repository;

import com.turno.los.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    @Query("SELECT a FROM Agent a WHERE a.id NOT IN (SELECT DISTINCT l.assignedAgent.id FROM Loan l WHERE l.applicationStatus = 'UNDER_REVIEW')")
    List<Agent> findAvailableAgents();
}
