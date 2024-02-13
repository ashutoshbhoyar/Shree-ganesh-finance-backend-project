package com.shreeganesh.loan.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.shreeganesh.loan.app.customerEntities.AllDocuments;
import com.shreeganesh.loan.app.customerEntities.CustomerAddress;
import com.shreeganesh.loan.app.customerEntities.CustomerBankDetails;
import com.shreeganesh.loan.app.customerEntities.CustomerDetails;
import com.shreeganesh.loan.app.customerEntities.CustomerProfession;
import com.shreeganesh.loan.app.customerEntities.CustomerVehicleInformation;
import com.shreeganesh.loan.app.customerEntities.Dealer;
import com.shreeganesh.loan.app.customerEntities.DealerBankDetails;
import com.shreeganesh.loan.app.customerEntities.Enquiry;
import com.shreeganesh.loan.app.customerEntities.Ledger;
import com.shreeganesh.loan.app.customerEntities.LoanDisbursement;
import com.shreeganesh.loan.app.customerEntities.LocalAddress;
import com.shreeganesh.loan.app.customerEntities.PermanentAddress;
import com.shreeganesh.loan.app.customerEntities.SanctionLetter;



@SpringBootApplication
public class ShreeGaneshFinanceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ShreeGaneshFinanceApplication.class, args);

		System.out.println("Jai GAnesh");

		// ObjectMapper mapper = new ObjectMapper();
		
		// try {
		// 	System.out.println(mapper.writeValueAsString(new SanctionLetter()));

		// } catch (JsonProcessingException e) {


		// 	e.printStackTrace();
		// }
	}
}
