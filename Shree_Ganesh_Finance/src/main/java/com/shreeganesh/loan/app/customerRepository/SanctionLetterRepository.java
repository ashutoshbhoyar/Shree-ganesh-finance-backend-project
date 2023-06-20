package com.shreeganesh.loan.app.customerRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreeganesh.loan.app.customerEntities.CustomerDetails;

public interface SanctionLetterRepository extends JpaRepository<CustomerDetails, Integer>{

	List<CustomerDetails> findAllByCustomerStatus(String customerStatus);

}
