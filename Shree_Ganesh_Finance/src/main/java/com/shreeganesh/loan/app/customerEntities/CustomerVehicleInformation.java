
package com.shreeganesh.loan.app.customerEntities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVehicleInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerVehicleId;
	private String customerVehicleModel;
	private String customerVehicleChasisNo;
	private String customerVehicleNo;
	private String customerVehicleRcNo;
	private Double customerVehiclePrice;

}


