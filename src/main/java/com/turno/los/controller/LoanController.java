package com.turno.los.controller;

import com.turno.los.dto.LoanRequestDTO;
import com.turno.los.model.enums.*;
import com.turno.los.model.Loan;
import com.turno.los.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    private final LoanService loanService;
    @PostMapping
    public ResponseEntity<?> createLoan( @Valid @RequestBody LoanRequestDTO loanRequest) {
        return ResponseEntity.ok(loanService.createLoan(loanRequest));
    }

    @GetMapping("/status-count")
    public Map<ApplicationStatus, Long> getStatusCount() {
        return loanService.getLoanStatusCount();
    }

    @GetMapping
    public Page<Loan> getLoansByStatus(@RequestParam ApplicationStatus status,
                                       @RequestParam int page,
                                       @RequestParam int size) {
        return loanService.getLoansByStatus(status, page, size);
    }

    @GetMapping("/customers/top")
    public List<Map<String, Object>> getTopCustomers() {
        return loanService.getTopCustomers();
    }
}
