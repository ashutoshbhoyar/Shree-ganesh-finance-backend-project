package com.shreeganesh.loan.app.customerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreeganesh.loan.app.customerEntities.Ledger;
import com.shreeganesh.loan.app.customerServiceInterfaces.LedgerService;

@RestController
@RequestMapping("/LedgerInstallmentController")
public class LedgerInstallmentController {

	@Autowired
	LedgerService ledgerService;

	@PutMapping("/payMonthlyEmi/{customerId}/{emiStatus}")
	public ResponseEntity<Ledger> payMonthlyEmi(@PathVariable("customerId") Integer customerId,
			@PathVariable("emiStatus") String emiStatus) {
	Ledger payMonthlyEmi = ledgerService.payMonthlyEmi(customerId, emiStatus);
		if (payMonthlyEmi != null) {
			return new ResponseEntity<Ledger>(payMonthlyEmi, HttpStatus.OK);
		} else {
			return new ResponseEntity<Ledger>( HttpStatus.NO_CONTENT);

		}
	}
}