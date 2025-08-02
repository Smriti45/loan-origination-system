package com.turno.los.controller;

import com.turno.los.dto.LoanRequestDTO;
import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import com.turno.los.model.enums.LoanTypes;
import com.turno.los.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLoan() {
        LoanRequestDTO dto = new LoanRequestDTO();
        dto.setLoanId("L001");
        dto.setCustomerName("Alice");
        dto.setCustomerPhone("9999999999");
        dto.setLoanAmount(BigDecimal.valueOf(50000));
        dto.setLoanType(LoanTypes.PERSONAL);

        Loan mockLoan = Loan.builder()
                .loanId("L001")
                .customerName("Alice")
                .customerPhone("9999999999")
                .loanAmount(BigDecimal.valueOf(50000))
                .loanType(LoanTypes.PERSONAL)
                .applicationStatus(ApplicationStatus.APPLIED)
                .build();

        when(loanService.createLoan(dto)).thenReturn(mockLoan);

        ResponseEntity<?> response = loanController.createLoan(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Loan);
        Loan result = (Loan) response.getBody();
        assertEquals("L001", result.getLoanId());
    }

    @Test
    void testGetStatusCount() {
        Map<ApplicationStatus, Long> mockMap = Map.of(
                ApplicationStatus.APPLIED, 4L,
                ApplicationStatus.APPROVED_BY_AGENT, 2L
        );

        when(loanService.getLoanStatusCount()).thenReturn(mockMap);

        Map<ApplicationStatus, Long> response = loanController.getStatusCount();

        assertEquals(4L, response.get(ApplicationStatus.APPLIED));
        assertEquals(2L, response.get(ApplicationStatus.APPROVED_BY_AGENT));
    }

    @Test
    void testGetLoansByStatus() {
        Loan loan = Loan.builder()
                .loanId("L100")
                .applicationStatus(ApplicationStatus.APPLIED)
                .build();

        Page<Loan> mockPage = new PageImpl<>(List.of(loan));

        when(loanService.getLoansByStatus(ApplicationStatus.APPLIED, 0, 1))
                .thenReturn(mockPage);

        Page<Loan> result = loanController.getLoansByStatus(ApplicationStatus.APPLIED, 0, 1);

        assertEquals(1, result.getTotalElements());
        assertEquals("L100", result.getContent().get(0).getLoanId());
    }

    @Test
    void testGetTopCustomers() {
        List<Map<String, Object>> mockList = List.of(
                Map.of("customerName", "Alice", "loanCount", 5),
                Map.of("customerName", "Bob", "loanCount", 3)
        );

        when(loanService.getTopCustomers()).thenReturn(mockList);

        List<Map<String, Object>> result = loanController.getTopCustomers();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).get("customerName"));
        assertEquals(3, result.get(1).get("loanCount"));
    }
}
