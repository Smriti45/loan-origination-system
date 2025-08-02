package com.turno.los.service.impl;

import com.turno.los.dto.LoanRequestDTO;
import com.turno.los.exception.DuplicateResourceException;
import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.repository.LoanRepository;
import com.turno.los.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService {

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    private final LoanRepository loanRepository;
    @Override
    public Loan createLoan(LoanRequestDTO dto) {
        if (loanRepository.existsByLoanId(dto.getLoanId())) {
            throw new DuplicateResourceException("Loan ID already exists.");
        }

        Loan loan = new Loan();
        loan.setLoanId(dto.getLoanId());
        loan.setCustomerName(dto.getCustomerName());
        loan.setCustomerPhone(dto.getCustomerPhone());
        loan.setLoanAmount(dto.getLoanAmount());
        loan.setLoanType(dto.getLoanType());
        loan.setApplicationStatus(ApplicationStatus.APPLIED);
        loan.setCreatedAt(LocalDateTime.now());

        return loanRepository.save(loan);
    }

    @Override
    public Map<ApplicationStatus, Long> getLoanStatusCount() {
        List<Object[]> result = loanRepository.countByStatus();
        return result.stream().collect(Collectors.toMap(
                r -> (ApplicationStatus) r[0], r -> (Long) r[1]
        ));
    }

    @Override
    public Page<Loan> getLoansByStatus(ApplicationStatus status, int page, int size) {
        return loanRepository.findByApplicationStatus(status, (Pageable) PageRequest.of(page, size));
    }

    @Override
    public List<Map<String, Object>> getTopCustomers() {
        List<Object[]> results = loanRepository.findTopCustomers(
                List.of(ApplicationStatus.APPROVED_BY_SYSTEM, ApplicationStatus.APPROVED_BY_AGENT),
                (Pageable) PageRequest.of(0, 3)
        );
        return results.stream().map(r -> Map.of("customerName", r[0], "loanCount", r[1])).collect(Collectors.toList());
    }
}