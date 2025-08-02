package com.turno.los.model;

import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.model.enums.LoanTypes;
import jakarta.persistence.*;
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
    @Id
    private String loanId;
    private String customerName;
    private String customerPhone;
    private BigDecimal loanAmount;

    @Enumerated(EnumType.STRING)
    private LoanTypes loanType;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    private LocalDateTime createdAt;

    @ManyToOne
    private Agent assignedAgent;

}

