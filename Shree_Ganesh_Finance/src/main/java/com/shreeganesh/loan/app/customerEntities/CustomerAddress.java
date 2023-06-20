
package com.shreeganesh.loan.app.customerEntities;

import javax.annotation.Generated;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GeneratorType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddress {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer  customerAddressId;
	@OneToOne(cascade = CascadeType.ALL)
	private PermanentAddress permanentAddress;
	@OneToOne(cascade = CascadeType.ALL)
	private LocalAddress localAddress;

}

