package com.shreeganesh.loan.app.customerRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreeganesh.loan.app.customerEntities.LoanDisbursement;


public interface LoanDisbursementRepository extends JpaRepository<LoanDisbursement, Integer> {

}
