package com.shreeganesh.loan.app.customerController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreeganesh.loan.app.customerEntities.CustomerDetails;
import com.shreeganesh.loan.app.customerServiceInterfaces.SanctionLetterService;

@CrossOrigin("*")
@RestController
@RequestMapping("/sanctionletter")
public class SanctionLetterController {

	@Autowired
	SanctionLetterService sls;

	@PutMapping("/generateSanctionLetterByCustomer")
	public ResponseEntity<CustomerDetails> updateSanctionLetter(@RequestBody CustomerDetails customerDetails) {
		sls.updateSanctionLetter(customerDetails);
		return new ResponseEntity<CustomerDetails>(HttpStatus.CREATED);
	}

	@GetMapping("/getAllGenratedSanctionByCm")
	public ResponseEntity<List<CustomerDetails>> getAllGenratedSanction() {

		List<CustomerDetails> genratedSanction = sls.getAllGenratedSanction();

		return new ResponseEntity<List<CustomerDetails>>(genratedSanction, HttpStatus.OK);
	}

	@GetMapping("/getAllSanctionLetterApprovedCustomers")
	public ResponseEntity<List<CustomerDetails>> getAllSanctionLetterApprovedCustomers() {

		List<CustomerDetails> customers = sls.getCustomersBySanctionLetterApproved();

		return new ResponseEntity<List<CustomerDetails>>(customers, HttpStatus.OK);
	}

	@PutMapping("/customerApplicationStatusSanctioned/{customerId}/{customerStatus}")
	public ResponseEntity<CustomerDetails> changeCustomerApplicationStatusSanctioned(
			@PathVariable("customerId") Integer customerId, @PathVariable("customerStatus") String customerStatus) {

		System.out.println(customerId + " " + customerStatus);
		CustomerDetails customerDetails = sls.changeCustomerFormStatusSanctioned(customerId, customerStatus);

		if (customerDetails != null) {
			return new ResponseEntity<CustomerDetails>(customerDetails, HttpStatus.OK);
		} else {
			return new ResponseEntity<CustomerDetails>(HttpStatus.NO_CONTENT);

		}
	}

	@GetMapping("/getAllSanctionLetterSignByCustomer")
	public ResponseEntity<List<CustomerDetails>> getAllSanctionLetterSignByCustomer() {

		List<CustomerDetails> signByCustomer = sls.getAllSanctionLetterSignByCustomer();

		return new ResponseEntity<List<CustomerDetails>>(signByCustomer, HttpStatus.OK);

	}

}
