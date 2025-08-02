package com.turno.los.repository;

import com.turno.los.model.Loan;
import com.turno.los.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByApplicationStatus(ApplicationStatus status);
    Page<Loan> findByApplicationStatus(ApplicationStatus status, Pageable pageable);

    @Query("SELECT l.customerName, COUNT(l) FROM Loan l WHERE l.applicationStatus IN :statuses GROUP BY l.customerName ORDER BY COUNT(l) DESC")
    List<Object[]> findTopCustomers(@Param("statuses") List<ApplicationStatus> statuses, Pageable pageable);

    @Query("SELECT l.applicationStatus, COUNT(l) FROM Loan l GROUP BY l.applicationStatus")
    List<Object[]> countByStatus();
}

