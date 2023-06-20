

package com.shreeganesh.loan.app.customerEntities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@AllArgsConstructor					
@NoArgsConstructor
public class CustomerProfession {
	
	 @Id
		@GeneratedValue(strategy = GenerationType.AUTO)
     private Integer professionId;
	 private String professionType;
	 private Double professionMonthlyIncome;
	 private String professionDesignation;
	 


}

