package com.turno.los.service.impl;

import com.turno.los.dto.LoanRequestDTO;
import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.model.enums.LoanTypes;
import com.turno.los.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    void testCreateLoan() {
        LoanRequestDTO dto = new LoanRequestDTO();
        dto.setLoanId("L001");
        dto.setCustomerName("John Doe");
        dto.setCustomerPhone("9876543210");
        dto.setLoanAmount(BigDecimal.valueOf(50000));
        dto.setLoanType(LoanTypes.PERSONAL);

        Loan loan = Loan.builder()
                .loanId(dto.getLoanId())
                .customerName(dto.getCustomerName())
                .customerPhone(dto.getCustomerPhone())
                .loanAmount(dto.getLoanAmount())
                .loanType(dto.getLoanType())
                .applicationStatus(ApplicationStatus.APPLIED)
                .createdAt(LocalDateTime.now())
                .build();

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan savedLoan = loanService.createLoan(dto);

        assertEquals(dto.getLoanId(), savedLoan.getLoanId());
        assertEquals(ApplicationStatus.APPLIED, savedLoan.getApplicationStatus());
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void testGetLoanStatusCount() {
        when(loanRepository.countByStatus()).thenReturn(
                List.of(new Object[]{ApplicationStatus.APPLIED, 5L}, new Object[]{ApplicationStatus.APPROVED_BY_SYSTEM, 3L})
        );

        Map<ApplicationStatus, Long> statusCount = loanService.getLoanStatusCount();

        assertEquals(2, statusCount.size());
        assertEquals(5L, statusCount.get(ApplicationStatus.APPLIED));
        assertEquals(3L, statusCount.get(ApplicationStatus.APPROVED_BY_SYSTEM));
    }

    @Test
    void testGetLoansByStatus() {
        Page<Loan> mockPage = new PageImpl<>(List.of(new Loan(), new Loan()));
        when(loanRepository.findByApplicationStatus(eq(ApplicationStatus.APPLIED), any(PageRequest.class)))
                .thenReturn(mockPage);

        Page<Loan> result = loanService.getLoansByStatus(ApplicationStatus.APPLIED, 0, 2);

        assertEquals(2, result.getContent().size());
    }

    @Test
    void testGetTopCustomers() {
        when(loanRepository.findTopCustomers(
                anyList(),
                (Pageable) any(PageRequest.class)
        )).thenReturn(List.of(new Object[]{"Alice", 3L}, new Object[]{"Bob", 2L}));

        List<Map<String, Object>> topCustomers = loanService.getTopCustomers();

        assertEquals(2, topCustomers.size());
        assertEquals("Alice", topCustomers.get(0).get("customerName"));
        assertEquals(3L, topCustomers.get(0).get("loanCount"));
    }
}
