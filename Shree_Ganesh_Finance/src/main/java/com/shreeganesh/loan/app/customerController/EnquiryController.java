
package com.shreeganesh.loan.app.customerController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreeganesh.loan.app.customerEntities.Enquiry;
import com.shreeganesh.loan.app.customerServiceInterfaces.EnquiryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/enquiry")
public class EnquiryController {

	
	@Autowired
	EnquiryService enquiryService;
	
	@PostMapping("/insert")
	public ResponseEntity<Enquiry> insertData(@RequestBody Enquiry enq){
		
		Enquiry enquiry=enquiryService.insertData(enq);
		
		return new ResponseEntity<Enquiry>(enquiry,HttpStatus.CREATED);
	}
	


	@GetMapping("/getAllEnquiries")
	public ResponseEntity<List<Enquiry>> getAllEnquiries(){
		List<Enquiry> enquiries = enquiryService.getAllEnquiries();
		return new ResponseEntity<List<Enquiry>>(enquiries,HttpStatus.OK);
	}
	
	@GetMapping("/getEligibleEnquiries")
	public  ResponseEntity<List<Enquiry>> getEligibleEnquiries(){
		
		List<Enquiry> cibilOkEnquiries = enquiryService.getCibilOkEnquiries();
		
		return new ResponseEntity<List<Enquiry>>(cibilOkEnquiries,HttpStatus.OK);
	}




	

}

