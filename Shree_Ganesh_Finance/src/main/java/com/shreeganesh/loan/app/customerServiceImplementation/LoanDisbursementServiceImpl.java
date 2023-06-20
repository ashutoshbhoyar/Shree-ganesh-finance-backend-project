package com.shreeganesh.loan.app.customerServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import com.shreeganesh.loan.app.customerEntities.CustomerDetails;
import com.shreeganesh.loan.app.customerEntities.Enquiry;
import com.shreeganesh.loan.app.customerEntities.LoanDisbursement;
import com.shreeganesh.loan.app.customerEnum.CustomerStatus;
import com.shreeganesh.loan.app.customerRepository.CustomerRepository;
import com.shreeganesh.loan.app.customerRepository.EnquiryRepository;
import com.shreeganesh.loan.app.customerRepository.LoanDisbursementRepository;
import com.shreeganesh.loan.app.customerServiceInterfaces.LoanDisbursementService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoanDisbursementServiceImpl implements LoanDisbursementService {

	@Autowired
	LoanDisbursement loan;
	
	@Value("${spring.mail.username}") // @Value is used for binding property to variable....
	private String fromEmail;
	
	@Autowired
	JavaMailSender sender;

	@Autowired
	CustomerRepository customerRepository;
	

	@Autowired
	EnquiryRepository enquiryRepository;

	private Logger logger = LoggerFactory.getLogger(LoanDisbursement.class);

	@Override
	public CustomerDetails insertData(Integer customerid) {

		Optional<CustomerDetails> optional = customerRepository.findById(customerid);

		if (optional.isPresent()) {

			CustomerDetails customer = optional.get();

			Date date = new Date();

			customer.getCustomerloandisbursement().setLoanNo(ThreadLocalRandom.current().nextLong(999, 9999));
			customer.getCustomerloandisbursement()
					.setTotalLoanAmount(customer.getCustomerSanctionLetter().getLoanAmountSanctioned());
			customer.getCustomerloandisbursement()
					.setTransferAmount(customer.getCustomerSanctionLetter().getLoanAmountSanctioned());
			customer.getCustomerloandisbursement()
					.setDealerBankDetails(customer.getCustomerDealer().getDealerBankDetails());
			customer.getCustomerloandisbursement().setPaymentStatus(String.valueOf(CustomerStatus.loandisbursed));
			customer.setCustomerStatus(String.valueOf(CustomerStatus.loandisbursed));
			customer.getCustomerloandisbursement().setAmountPaidDate(date);


			Optional<Enquiry> optional1 = enquiryRepository.findById(customer.getCustomerId());
			if (optional.isPresent()) {
				Enquiry enquiry = optional1.get();
				enquiry.setEnquiryStatus(String.valueOf(CustomerStatus.loandisbursed));
				enquiryRepository.save(enquiry);

			}

			logger.info("Loan Disbursement PDF started");

			String title = "Shree Ganesh Finace";
			String title2 = "\nLoan Disbursement Letter";
			String content1 = "Dear , " + customer.getCustomerFirstName()+" "+customer.getCustomerLastName()
					+ " This letter is to inform you that we have successfully disbursed the loan funds for your car loan. "
					+ "The loan amount of " + customer.getCustomerSanctionLetter().getLoanAmountSanctioned()
					+ " has been transferred to the dealer's account and the car is ready "
					+ "for delivery.\n\nWe are pleased to have been able to provide you with the financing you needed to purchase the car of your dreams. "
					+ "We hope that you are satisfied with our service and that you enjoy driving your new car."
					+ "\n\nThanks for chossing shree Ganesh Finace, hope your expiriance is pleasant.\n\nSincerely,\nShree Ganesh Finace";

			ByteArrayOutputStream opt = new ByteArrayOutputStream();
			
			Document document = new Document();
			PdfWriter.getInstance(document, opt);
			
			document.open();

			Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
			Paragraph titlepara = new Paragraph(title, font1);
			Paragraph titlepara2 = new Paragraph(title2, font1);
			
			titlepara.setAlignment(Element.ALIGN_CENTER);
			titlepara2.setAlignment(Element.ALIGN_CENTER);
			
			document.add(titlepara);
			document.add(titlepara2);

			Paragraph paracontent1 = new Paragraph(content1);
			document.add(paracontent1);

			document.close();
			
			ByteArrayInputStream byt = new ByteArrayInputStream(opt.toByteArray());

			MimeMessage mimemessage = sender.createMimeMessage();

			try {
				MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimemessage, true);
				mimemessageHelper.setFrom(fromEmail);
				mimemessageHelper.setTo(customer.getCustomerEmail());
				mimemessageHelper.setSubject("Shree Ganesh Finance regarding to Car loan");
				mimemessageHelper.setText(
						"\n\nDear Dealer, \n\nWe have recived sanction letter & happy to let you that we will get sanction amount");
				byte[] bytearray = byt.readAllBytes();

				mimemessageHelper.addAttachment("loanDisbursmentletter.pdf", new ByteArrayResource(bytearray));
				sender.send(mimemessage);
				customer.getCustomerloandisbursement().setLoanDisbursementLetter(bytearray);
				
				customerRepository.save(customer);

			} catch (Exception e) {

				e.printStackTrace();
			}
			return customer;
		} else {
			return null;
		}

	}

}
