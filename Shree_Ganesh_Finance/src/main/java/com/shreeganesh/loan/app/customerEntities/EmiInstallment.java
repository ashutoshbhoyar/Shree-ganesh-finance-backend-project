package com.shreeganesh.loan.app.customerEntities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmiInstallment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer emiInstallmentId;
	private LocalDate installmentDate;
	private Double emiAmount;
	private String emiStatus;
	

}
