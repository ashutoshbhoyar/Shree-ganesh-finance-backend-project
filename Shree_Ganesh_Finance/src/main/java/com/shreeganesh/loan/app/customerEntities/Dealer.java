
package com.shreeganesh.loan.app.customerEntities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dealer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer dealerId;
	private String dealerName;
	private String dealerAddress;
	private String dealerEmail;
	@OneToOne(cascade = CascadeType.ALL)
	private DealerBankDetails dealerBankDetails;

}


