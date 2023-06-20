
package com.shreeganesh.loan.app.customerServiceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.shreeganesh.loan.app.customerEntities.Enquiry;
import com.shreeganesh.loan.app.customerEnum.CustomerStatus;
import com.shreeganesh.loan.app.customerRepository.EnquiryRepository;
import com.shreeganesh.loan.app.customerServiceInterfaces.EnquiryService;
import com.shreeganesh.loan.app.customerUtil.Cibil;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.shreeganesh.loan.app.customerEntities.Enquiry;
import com.shreeganesh.loan.app.customerEnum.CustomerStatus;
import com.shreeganesh.loan.app.customerRepository.EnquiryRepository;
import com.shreeganesh.loan.app.customerServiceInterfaces.EnquiryService;
import com.shreeganesh.loan.app.customerUtil.Cibil;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	EnquiryRepository enquiryRepository;

	@Value("${spring.mail.username}") // @Value is used for binding property to variable....
	private String fromEmail;

	@Autowired
	JavaMailSender sender;

	@Override
	public Enquiry insertData(Enquiry enq) {

		SimpleMailMessage message = new SimpleMailMessage();
		// For Sending an Email About Cibil Score

		Cibil cibil = new Cibil();
		Integer cibilChecked = cibil.cibilCheck(enq.getEnquiryPanCard());
		if (cibilChecked >= 750) {

			message.setTo(enq.getEnquiryEmail());
			message.setFrom(fromEmail);
			message.setSubject("Regarding Car Loan For Documentation of Applicant Name :" + enq.getEnquiryFirstName()
					+ " " + enq.getEnquiryLastName());
			message.setText("Your Cibil Score is Approved and You are Eligible\n" + "For Further Process.\n"
					+ "We are Happy to inform you that your car loan request has been approved and is currently being processed.\n"
					+ "Further, we inform you that we have sent an email containing attched documents.\n"
					+ "Also, we have sent you the terms and conditions of the loans sanctioned. \n"
					+ "we would like to schedule your meeting with the Realtion Officer of the company for any further information and clarification,\n"
					+ "we are happy to doing business with you.\n\n" + "List of Documents Required :- \n\n"
					+ "1.Adhar Card \n" + "2.pan Card \n" + "3.Income Tax Return of Last Two Years \n"
					+ "4.Salary Slips of Last Three Months \n" + "5.Passport Size Photograph \n"
					+ "6.Bank Passbook Copy \n" + "\n\n Thanking You.\n" + "Mr.Sagar \n" + "Branch Manager\n"
					+ "Shree Ganesh Finance Ltd. \n" + "Karvenagar\n" + "Pune, Maharashtra \n India-410302\n" + "\n"
					+ "Thank You For Banking With Us.\n\n" + "Shree Ganesh Finance Ltd...");

			sender.send(message);

			enq.setEnquiryStatus(String.valueOf(CustomerStatus.CibilOK));
		} else {

			message.setTo(enq.getEnquiryEmail());
			message.setFrom(fromEmail);
			message.setSubject("Regarding Car Loan For Documentation of Applicant Name :" + enq.getEnquiryFirstName()
					+ "" + enq.getEnquiryLastName());
			message.setText("Your Cibil Score is Rejected and You are Not Eligible\n" + "For Further Process.\n"
					+ "We are Not Happy to inform you that your car loan request has been Rejected and is currently being Not processed.\n"
					+ "\n\n Thanking You.\n" + "Mr.Sagar \n" + "Branch Manager\n" + "Shree Ganesh Finance Ltd. \n"
					+ "Karvenagar\n" + "Pune, Maharashtra \n India-410302\n" + "\n"
					+ "Thank You For Banking With Us.\n\n" + "Shree Ganesh Finance Ltd...");

			sender.send(message);

			enq.setEnquiryStatus(String.valueOf(CustomerStatus.CibilNotOK));
		}

		enq.setEnquiryCibilScore(cibilChecked);
		Enquiry enquiry = enquiryRepository.save(enq);
		System.out.println(enq);
		return enquiry;
	}

	@Override
	public List<Enquiry> getAllEnquiries() {
		return enquiryRepository.findAll();

	}

	@Override
	public List<Enquiry> getCibilOkEnquiries() {
		return enquiryRepository.findAllByEnquiryStatus(String.valueOf(CustomerStatus.CibilOK));


	}


}

