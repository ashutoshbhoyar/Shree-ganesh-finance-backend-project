
package com.shreeganesh.loan.app.customerEntities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {
	@Id
	private Integer customerId;
	private String customerFirstName;
	private String	customerMiddleName;
	private String customerLastName;
	private Long customerMobileNumber;
	private Double customerloanAmountRequired;
	private Integer customerLoanTenureInMonth;
    private Long customerAdditionalMobileNumber;
	private String customerPanCard;
	private Long customerAdhaarCard;
	private String customerDateOfBirth;
	private String customerEmail;
	private String customerGender;
	private String customerQualification;
	private Integer customerCibilScore;
	private String customerStatus;
	@OneToOne (cascade = CascadeType.ALL)
	private AllDocuments customerAllDocuments;
	@OneToOne (cascade = CascadeType.ALL)
	private CustomerAddress customerAddress;
	@OneToOne (cascade = CascadeType.ALL)
	private CustomerProfession customerProfession;
	@OneToOne (cascade = CascadeType.ALL)
	private Dealer customerDealer;
	@OneToOne (cascade = CascadeType.ALL)
	private LoanDisbursement customerloandisbursement;
	@OneToOne(cascade = CascadeType.ALL)
	private Ledger customerledger;
	@OneToOne (cascade = CascadeType.ALL)
	private SanctionLetter customerSanctionLetter;
	@OneToOne (cascade = CascadeType.ALL)
	private CustomerVehicleInformation customerVehicleInformation;
	@OneToOne (cascade = CascadeType.ALL)
	private CustomerBankDetails customerBankDetails;

}

