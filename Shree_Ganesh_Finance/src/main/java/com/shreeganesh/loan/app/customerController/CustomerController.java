package com.shreeganesh.loan.app.customerController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shreeganesh.loan.app.customerEntities.AllDocuments;
import com.shreeganesh.loan.app.customerEntities.CustomerDetails;
import com.shreeganesh.loan.app.customerServiceInterfaces.CustomerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/customer")
public class CustomerController 
{

	@Autowired
	CustomerService customerService;

	@PostMapping("/FillLoanApplicationForm")
	public ResponseEntity<CustomerDetails> fillLoanApplicationForm(
			@RequestPart("customerDetails") String customerDetails, @RequestPart("photo") MultipartFile photo,
			@RequestPart("addressProof") MultipartFile addressProof, @RequestPart("panCard") MultipartFile panCard,
			@RequestPart("incomeProof") MultipartFile incomeProof, @RequestPart("signature") MultipartFile signature,
			@RequestPart("thumb") MultipartFile thumb, @RequestPart("bankPassbook") MultipartFile bankPassbook,
			@RequestPart("drivingLicense") MultipartFile drivingLicense,
			@RequestPart("addharCard") MultipartFile addharCard)
	{ 
		ObjectMapper om = new ObjectMapper();
		try {

			CustomerDetails details = om.readValue(customerDetails, CustomerDetails.class);

			AllDocuments allDocuments = new AllDocuments();

			allDocuments.setPhoto(photo.getBytes());
			allDocuments.setAddressProof(addressProof.getBytes());
			allDocuments.setPanCard(panCard.getBytes());
			allDocuments.setIncomeProof(incomeProof.getBytes());
			allDocuments.setSignature(signature.getBytes());
			allDocuments.setThumb(thumb.getBytes());
			allDocuments.setBankPassbook(bankPassbook.getBytes());
			allDocuments.setDrivingLicense(drivingLicense.getBytes());
			allDocuments.setAddharCard(addharCard.getBytes());

			details.setCustomerAllDocuments(allDocuments);

			CustomerDetails fillLoanApplicationForm = customerService.fillLoanApplicationForm(details);
			return new ResponseEntity<CustomerDetails>(fillLoanApplicationForm, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<CustomerDetails>(HttpStatus.NO_CONTENT);
		}
	}

//	@GetMapping("/getSingleDataByCustomerId/{customerId}")
//	public ResponseEntity<CustomerDetails> getByCustomerId(@PathVariable("customerId") Integer customerId) {
//		CustomerDetails byCustomerIdDetails = customerService.getByCustomerId(customerId);
//		if (byCustomerIdDetails != null) {
//			return new ResponseEntity<CustomerDetails>(byCustomerIdDetails, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<CustomerDetails>(HttpStatus.NO_CONTENT);
//		}
//	}

	@GetMapping("/getAllCustomerFormData")
	public ResponseEntity<List<CustomerDetails>> getAllCustomerFormData()
	{
		List<CustomerDetails> allCustomerDetails = customerService.getAllCustomerDetails();

		return new ResponseEntity<List<CustomerDetails>>(allCustomerDetails, HttpStatus.OK);
	
	}
	
	@PutMapping("/customerApplicationStatus/{customerId}/{customerStatus}")
	public ResponseEntity<CustomerDetails> changeCustomerApplicationFormStatus(

			@PathVariable("customerId") Integer customerId, @PathVariable("customerStatus") String customerStatus) 
	{

		CustomerDetails customerDetails = customerService.changeCustomerFormStatus(customerId, customerStatus);

		if (customerDetails != null) {
			return new ResponseEntity<CustomerDetails>(customerDetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<CustomerDetails>(HttpStatus.NO_CONTENT);

		}
	}
	//to get all Document Verified Customer....
	@GetMapping("/getAllDocVerifiedCustomer")
	public ResponseEntity<List<CustomerDetails>> getAllDocVerifiedCustomer()
	{
		List<CustomerDetails> verifiedCustomer = customerService.getAllDocVerifiedCustomer();
		
		return new ResponseEntity<List<CustomerDetails>>(verifiedCustomer, HttpStatus.OK);
	}
	
	//to get all Document Rejected Customer....
	@GetMapping("/getAllDocRejectedCustomer")
	public ResponseEntity<List<CustomerDetails>> getAllDocRejectedCustomer()
	{
		List<CustomerDetails> rejectedCustomer = customerService.getAllDocRejectedCustomer();
		
		return new ResponseEntity<List<CustomerDetails>>(rejectedCustomer, HttpStatus.OK);
	}
	
	
	

}

