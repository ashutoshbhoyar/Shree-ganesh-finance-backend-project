package com.shreeganesh.loan.app.customerServiceInterfaces;

import com.shreeganesh.loan.app.customerEntities.Ledger;

public interface LedgerService {

	public Ledger payMonthlyEmi(Integer customerId, String emiStatus);

}
