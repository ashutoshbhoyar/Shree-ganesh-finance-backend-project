
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
public class LocalAddress {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer localAddressId;
	private String areaname;
	private String cityname;
	private String district;
	private String state;
	private Long pincode;
	private String houseNumber;
	private String streetName;


}

