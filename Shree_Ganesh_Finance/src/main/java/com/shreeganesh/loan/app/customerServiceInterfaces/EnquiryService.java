

package com.shreeganesh.loan.app.customerServiceInterfaces;

import java.util.List;

import com.shreeganesh.loan.app.customerEntities.Enquiry;

public interface EnquiryService {

	public Enquiry insertData(Enquiry enq);

	public List<Enquiry> getAllEnquiries();

	public List<Enquiry> getCibilOkEnquiries();

}
