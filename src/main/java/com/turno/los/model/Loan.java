package com.turno.los.model;

import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.model.enums.LoanTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LoanTypes getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanTypes loanType) {
        this.loanType = loanType;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Agent getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(Agent assignedAgent) {
        this.assignedAgent = assignedAgent;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    @Id
    @Column(unique = true, nullable = false)
    private String loanId;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    @Size(max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String customerPhone;
    @Column(nullable = false)
    @DecimalMin(value = "1.0", message = "Loan amount must be greater than 0")
    private BigDecimal loanAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanTypes loanType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agent assignedAgent;

}

