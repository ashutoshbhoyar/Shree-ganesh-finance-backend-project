

package com.shreeganesh.loan.app.customerEntities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ledgerId;
	private String ledgerCreatedDate;//
	private Double payableAmountwithInterest;//
	private Integer tenure;//
	private Double monthlyEMI;//
	private double amountPaidtillDate;//
	private double remainingAmount;//
	private LocalDate nextEmiDatestart;//
	private Integer defaulterCount;//
	private String currentMonthEmiStatus;//
	private LocalDate loanEndDate;//
	@OneToMany(cascade = CascadeType.ALL)
	private List<EmiInstallment> emiInstallment;
	private String loanStatus;//

}


