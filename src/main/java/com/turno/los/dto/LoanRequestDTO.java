package com.turno.los.dto;

import com.turno.los.model.enums.LoanTypes;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequestDTO {
    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

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

    private String loanId;
    private String customerName;
    private String customerPhone;
    private BigDecimal loanAmount;
    private LoanTypes loanType;
}
