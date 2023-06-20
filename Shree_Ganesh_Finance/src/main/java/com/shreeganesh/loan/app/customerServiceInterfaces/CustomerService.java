package com.shreeganesh.loan.app.customerServiceInterfaces;

import java.util.List;

import com.shreeganesh.loan.app.customerEntities.CustomerDetails;

public interface CustomerService {

	public CustomerDetails fillLoanApplicationForm(CustomerDetails details);

	public CustomerDetails getByCustomerId(Integer customerId);

	public List<CustomerDetails> getAllCustomerDetails();

	public CustomerDetails changeCustomerFormStatus(Integer customerId, String customerStatus);

	public List<CustomerDetails> getAllDocVerifiedCustomer();

	public List<CustomerDetails> getAllDocRejectedCustomer();


}
