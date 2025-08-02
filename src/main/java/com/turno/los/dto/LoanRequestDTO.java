package com.turno.los.dto;

import com.turno.los.model.enums.LoanTypes;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequestDTO {
    private String loanId;
    private String customerName;
    private String customerPhone;
    private BigDecimal loanAmount;
    private LoanTypes loanType;
}
