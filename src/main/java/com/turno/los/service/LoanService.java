package com.turno.los.service;

import com.turno.los.dto.LoanRequestDTO;
import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface LoanService {
    Loan createLoan(LoanRequestDTO loanRequestDTO);
    Map<ApplicationStatus, Long> getLoanStatusCount();
    Page<Loan> getLoansByStatus(ApplicationStatus status, int page, int size);
    List<Map<String, Object>> getTopCustomers();
}
